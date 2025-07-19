; listen port 6666 and respond "hello client" or "unrecognised greeting"

%define SYS_SOCKET     41
%define SYS_BIND       49
%define SYS_LISTEN     50
%define SYS_ACCEPT     43
%define SYS_RECV       45
%define SYS_SEND       44
%define SYS_CLOSE      3
%define AF_INET        2
%define SOCK_STREAM    1
%define IPPROTO_IP     0

section .data

    greeting db "hello server", 0
    greeting_len equ $ - greeting

    response_hello db "hello client", 0
    response_hello_len equ $ - response_hello

    response_unknown db "unrecognised greeting", 0
    response_unknown_len equ $ - response_unknown

section .bss

    client_buffer resb 128
    sockaddr resb 16       ; struct sockaddr_in
    client_sock resq 1
    server_sock resq 1

section .text

    global _start

_start:

    mov rax, SYS_SOCKET
    mov rdi, AF_INET
    mov rsi, SOCK_STREAM
    mov rdx, IPPROTO_IP
    syscall
    mov [server_sock], rax

    mov word [sockaddr], AF_INET
    mov word [sockaddr+2], 0x1a0a     ; htons(6666) = 0x1a0a
    mov dword [sockaddr+4], 0         ; 0.0.0.0

    ; bind(sock, sockaddr, 16)
    mov rax, SYS_BIND
    mov rdi, [server_sock]
    lea rsi, [sockaddr]
    mov rdx, 16
    syscall

    ; listen(sock, backlog=1)
    mov rax, SYS_LISTEN
    mov rdi, [server_sock]
    mov rsi, 1
    syscall

    ; accept(sock, NULL, NULL)
    mov rax, SYS_ACCEPT
    mov rdi, [server_sock]
    xor rsi, rsi
    xor rdx, rdx
    syscall
    mov [client_sock], rax

    ; recv(client_sock, buffer, 128, 0)
    mov rax, SYS_RECV
    mov rdi, [client_sock]
    lea rsi, [client_buffer]
    mov rdx, 128
    xor r10, r10      
    syscall
    mov rcx, rax      

    lea rsi, [client_buffer]
    lea rdi, [greeting]
    call strcmp
    cmp rax, 0
    jne .send_unknown

.send_hello:
    ; send(client_sock, "hello client", len, 0)
    mov rax, SYS_SEND
    mov rdi, [client_sock]
    lea rsi, [response_hello]
    mov rdx, response_hello_len
    xor r10, r10
    syscall
    jmp .close_all

.send_unknown:
    mov rax, SYS_SEND
    mov rdi, [client_sock]
    lea rsi, [response_unknown]
    mov rdx, response_unknown_len
    xor r10, r10
    syscall

.close_all:
    ; close(client_sock)
    mov rax, SYS_CLOSE
    mov rdi, [client_sock]
    syscall

    ; close(server_sock)
    mov rax, SYS_CLOSE
    mov rdi, [server_sock]
    syscall

    ; exit(0)
    mov rax, 60
    xor rdi, rdi
    syscall

; int strcmp(const char* s1, const char* s2)
; Returns 0 if equal
strcmp:
    push rbx
.next_char:
    mov al, [rdi]
    mov bl, [rsi]
    cmp al, bl
    jne .not_equal
    test al, al
    je .equal
    inc rdi
    inc rsi
    jmp .next_char
.not_equal:
    mov eax, 1
    pop rbx
    ret
.equal:
    xor eax, eax
    pop rbx
    ret
