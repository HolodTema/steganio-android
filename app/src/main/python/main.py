ALPHABET = "*abcdefghijkmnlopqrstuvwxyzABCDEFGHIJKMNLOPQRSTUVWXYZ абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"

def prepare_container(container: str):
    container = container.replace("\n", " ")
    while "  " in container:
        container = container.replace("  ", " ")
    return container


def prepare_secret(secret: str):
    secret_chars = set(secret)
    for secret_char in secret_chars:
        if secret_char not in ALPHABET:
            secret = secret.replace(secret_char, "")
    return secret


def encrypt_by_spaces(container: str, secret: str):
    prepare_secret(secret)
    prepare_container(container)
    
    caret = 0
    for ch in secret:
        code = (bin(ALPHABET.find(ch))[2:]).zfill(8)
        for code_ch in code:
            while container[caret] != " ":
                caret += 1
                if caret == len(container):
                    return ""

            if code_ch == "1":
                container = container[:caret] + " " + container[caret:]
                caret += 1

            caret += 1
    return container


def decrypt_by_spaces(encryption: str):
    flag_to_continue = False
    code = ""
    decryption = ""
    for i in range(len(encryption)):
        if flag_to_continue:
            flag_to_continue = False
            continue
        if encryption[i] == " ":
            if (i+1) < len(encryption) and encryption[i+1] == " ":
                code += "1"
                flag_to_continue = True
            else:
                code += "0"
            if len(code) == 8:
                print(code)
                decryption += ALPHABET[int(code, 2)]
                code = ""
    return decryption.replace("*", "")

