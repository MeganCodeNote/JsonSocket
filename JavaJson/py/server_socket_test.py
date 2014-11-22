import jsonsocket as js
""" This script is for tesing a Python JsonSocket client
    against a Java JsonSocket Sever library,

    try: diff 30m.txt data.txt to make sure data are properly send and received
"""
if __name__ == "__main__":

    # send the content over to localhost, port: 20000
    server = js.Server('127.0.0.1', 20000)

    # read the content of the file
    f = open('test/30m.txt', 'r')
    content = f.read()
    f.close()
    server.accept().send(content)

    # # receive the data back
    data = server.recv()
    server.close()
    f = open('test/data.txt', 'w')
    f.write(data)
    f.close()

    # # receive, save to file, send the same content back
    # data = server.accept().recv()
    # f = open('test/30m.txt', 'w')
    # f.write(data)
    # f.close()
    # server.send(data).close()

