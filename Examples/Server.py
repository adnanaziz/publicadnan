import SimpleHTTPServer
import SocketServer
import json

import sys

HEADER = \
'''
HTTP/1.1 200 OK
Content-Type: application/json
Server: Custom Python Server
Content-Length: '''

if len(sys.argv) > 1:
    PORT = int(sys.argv[1])
else:
    PORT = 8000

suggestions = []

def loadSuggestions():
    global suggestions
    romeoAll = open("romeo-full.txt").read()
    tmp1 = romeoAll.split(" ")
    tmp2 = []
    for t in tmp1:
        if t.isalpha(): 
            tmp2.append(t)
    suggestions = list(set(tmp2))
    suggestions.sort()
    print suggestions

def suggest(s):
    result = []
    for x in suggestions:
        if (x.startswith(s)):
            result.append(x)
    return result

class ServerHandler(SimpleHTTPServer.SimpleHTTPRequestHandler):

    def do_GET(self):
        SimpleHTTPServer.SimpleHTTPRequestHandler.do_GET(self)

    def do_POST(self):
        contentLength = int(self.headers.getheader('content-length'))
        s =  self.rfile.read(contentLength)
        S = json.loads(s)
        R = suggest(S['prefix'])
        r = json.dumps({"result": R})

        self.wfile.write(HEADER + str(len(r)) + "\n\n" + r)

loadSuggestions()
Handler = ServerHandler

httpd = SocketServer.TCPServer(("", PORT), Handler)

print "Serving at: http://localhost:%s" % (PORT,)
httpd.serve_forever()
