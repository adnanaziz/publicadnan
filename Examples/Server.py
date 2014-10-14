import SimpleHTTPServer
import SocketServer
import logging
import cgi

import sys

HEADER = \
'''
HTTP/1.1 200 OK
Content-Type: application/json
Server: Custom Python Server
Content-Length: '''


if len(sys.argv) > 2:
    PORT = int(sys.argv[2])
    I = sys.argv[1]
elif len(sys.argv) > 1:
    PORT = int(sys.argv[1])
    I = ""
else:
    PORT = 8000
    I = ""


class ServerHandler(SimpleHTTPServer.SimpleHTTPRequestHandler):

    def do_GET(self):
        logging.warning("======= GET STARTED =======")
        logging.warning(self.headers)
        SimpleHTTPServer.SimpleHTTPRequestHandler.do_GET(self)

    def do_POST(self):
        logging.warning("======= POST STARTED =======")
        logging.warning(self.headers)
        logging.warning(self.rfile)
        print self.headers.getheader('content-length')
        s =  self.rfile.read(int(self.headers.getheader('content-length')))
        print "s = " + s
        import json
        H = json.loads(s)
        for key in H:
            print "key = " + key

        # self.wfile.write(HEADER + str(len(s)) + "\n" + s)
        print HEADER + str(len(s)) + "\r\n\r\n" + s
        self.wfile.write(HEADER + str(len(s)) + "\n\n" + s)

Handler = ServerHandler

httpd = SocketServer.TCPServer(("", PORT), Handler)

print "@rochacbruno Python http server version 0.1 (for testing purposes only)"
print "Serving at: http://%(interface)s:%(port)s" % dict(interface=I or "localhost", port=PORT)
httpd.serve_forever()
