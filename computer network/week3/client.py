from socket import *
serverName = '10.0.0.11'
serverPort = 12000
clientSocket = socket(AF_INET, SOCK_DGRAM)
message = input("Input lowercase sentence:")
clientSocket.sendto(message.encode(),(serverName, serverPort))
modifiedMessage, serverAddress = clientSocket.recv(2048)
print(modifiedMessage.decode())
clientSocket.close()