import jsonsocket as js
""" This script is for tesing a Python JsonSocket client
    against a Java JsonSocket Sever library,

    try: diff 30m.txt data.txt to make sure data are properly send and received
"""
if __name__ == "__main__":
    # send the content over to localhost, port: 20000
    client = js.Client()
    client.connect('127.0.0.1', 20000)
    while True:
        data = client.recv()
        if data is None: # no more data comming in
            break
        else:
            f = open('test/data.txt', 'a')
            f.write(data)
            f.close()  
    client.close()

