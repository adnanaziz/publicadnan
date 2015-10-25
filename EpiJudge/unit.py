'''
Tests EpiJudge backend

@author: Adnan
'''

import httplib
import json
import re
from datetime import datetime
import sys
import time
import calendar
import time
import pytz
from mock import MagicMock
import os

import requests  # for multipart upload

import random
import inspect

from sets import Set

EPI_JUDGE_SERVER = '10.0.1.7'
EPI_JUDGE_PORT = 8000

# use to slow down server hits
def my_sleep(T):
    time.sleep(T)

# use to turn on/off assertion checking
soft_fail_count = 0
fail_count = 0
pass_count = 0

def haskey(f, k):
    return k in f

import inspect

def my_assert(cond, soft=True):
    global soft_fail_count
    global fail_count
    global pass_count
    if (not cond):
        if (not soft):
            fail_count = fail_count + 1
        else:
            soft_fail_count = soft_fail_count + 1
    else:
        pass_count = pass_count + 1
        print "\n----" + bcolors.OKGREEN 
        print "Pass check: " + str(inspect.stack()[1][4])
        print "Pass function, line: " + str(inspect.stack()[1][3]) + ":" + str(inspect.stack()[1][2])
        print bcolors.ENDC
    if not soft:
        assert cond
    if soft and not cond:
        #print "Failure: " + str(inspect.stack()[1])
        #print "Failure 0: " + str(inspect.stack()[1][0])
        #print "Failure 1: " + str(inspect.stack()[1][1])
        print "\n----" + bcolors.FAIL 
        print "Failed check: " + str(inspect.stack()[1][4])
        print "Failed function, line: " + str(inspect.stack()[1][3]) + ":" + str(inspect.stack()[1][2])
        print bcolors.ENDC
    # pass

# to color code outputs:
# http://stackoverflow.com/questions/287871/print-in-terminal-with-colors-using-python


class bcolors:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'


def prettyprint_json(jsonObj):
    return json.dumps(jsonObj, sort_keys=True, indent=4)


# use this to abstract server parameters, e.g., port, domain name
class server:

    def __init__(self):
        self.server = EPI_JUDGE_SERVER if not (
                "EPI_JUDGE_SERVER" in os.environ) else os.environ["EPI_JUDGE_SERVER"]
        self.port = EPI_JUDGE_PORT if not ("EPI_JUDGE_PORT" in os.environ) else os.environ["EPI_JUDGE_PORT"]
        self.conn = httplib.HTTPConnection(self.server, self.port)
        self.headers = { "Content-type": "application/json", "Accept": "text/plain", 'User-Agent': 'EPI-Judge Python IOS Test Script'}
        self.url = "http://" + self.server + ":" +  str(self.port) + "/"
        self.geturl = "http://" + self.server + ":" + str(self.port) + "/"


class codes:

    def __init__(self):
        self.success = {"status": "SUCCESS", "code":
                200, "message": "Successful service call"}

        self.authFail = {
                "status": "FAIL", "code": 401, "message": "Authorization failure"}
        self.accessForbidden = {
                "status": "FAIL", "code": 403, "message": "Access forbidden"}
        self.notFound = {
                "status": "FAIL", "code": 404, "message": "Item not found"}
        self.notAllowed = {
                "status": "FAIL", "code": 405, "message": "Method not allowed"}
        self.notAllowed = {
                "status": "FAIL", "code": 406, "message": "Illegal input"}
        self.conflict = {"status": "FAIL", "code": 409, "message": "Conflict"}
            # e.g., if item being added already exists in DB
        self.notAllowed = {
                "status": "FAIL", "code": 413, "message": "Input too large"}
        self.unknownServerError = {
                "status": "FAIL", "code": 500, "message": "Unknown server error"}
        self.notImplemented = {
                "status": "FAIL", "code": 501, "message": "Service not implemented"}

class R:
    server = server()

def send_request(service, req, verbose=True):
    url = R.server.url + service
    reqString = json.dumps(req)
    print "Requesting service at " + bcolors.OKGREEN + url + bcolors.ENDC
    print bcolors.HEADER + "\n>>> Request JSON = \n" + bcolors.ENDC + prettyprint_json(req)
    res = requests.post("http://10.0.1.7:8000/compile", data=req)
    jsonres = json.loads(res.text)
    if verbose:
        jsonrestext =  prettyprint_json(jsonres)
    else:
        jsonrestext =  prettyprint_json(jsonres)[:20]
    print bcolors.HEADER + "\n>>> Response JSON = \n" + bcolors.ENDC + jsonrestext + "\n\n"
    return jsonres


def send_simple_get_request(getIt):
    url = R.server.geturl + getIt
    print "Requesting service at " + bcolors.OKGREEN + url + bcolors.ENDC
    R.server.conn.request("GET", url)
    res = R.server.conn.getresponse()
    resString = res.read()
    jsonres = json.loads(resString)
    print bcolors.HEADER + "\n>>> Response JSON = \n" + bcolors.ENDC + prettyprint_json(jsonres) + "\n\n"
    return jsonres


def send_get_request(service, urlString):
    url = R.server.url + service + "/" + urlString
    print url
    print "Requesting service at " + bcolors.OKGREEN + url + bcolors.ENDC
    R.server.conn.request("GET", url)
    res = R.server.conn.getresponse()
    resString = res.read()
    jsonres = json.loads(resString)
    print bcolors.HEADER + "\n>>> Response JSON = \n" + bcolors.ENDC + prettyprint_json(jsonres) + "\n\n"
    return jsonres

# user requests, specifies the user object (FBid). authenticate with loginCookie.
# initial request, i.e., createOrUpdateFBUser used the FB auth token.

HELLO_WORLD = '''
class HelloWorld {
  public static void main(String[] args) {
    System.out.println("Hello World!");
  }
}'''

FAIL_COMPILE_1 = '''
foo HelloWorld {
  public static void main(String[] args) {
    System.out.println("Hello World!");
    System.err.println("Danger Will Robinson!");
  }
}'''

FAIL_RUN_1 = '''
class HelloWorld {
  public static void main(String[] args) {
    System.out.println("Hello World!");
    throw new IllegalArgumentException();
  }
}'''

FAIL_RUN_2 = '''
class HelloWorld {
  public static void main(String[] args) {
    System.exit(-1);
  }
}'''

FAIL_RUN_3 = '''
class HelloWorld {
  public static void main(String[] args) {
    System.out.println("Hello");
    assert(false);
    System.out.println("World!");
  }
}'''


def test_hello_world():
    helloDict = {
            "language":8,
            "code": HELLO_WORLD,
            "stdin": "123"
    }
    #res = send_request("compile", helloDict)

    helloDict["code"] = FAIL_COMPILE_1
    #res = send_request("compile", helloDict)

    helloDict["code"] = FAIL_RUN_1
    #res = send_request("compile", helloDict)

    helloDict["code"] = FAIL_RUN_2
    #res = send_request("compile", helloDict)

    helloDict["code"] = FAIL_RUN_3
    #res = send_request("compile", helloDict)

    helloDict["code"] = HELLO_WORLD
    for i in range(60):
        print "Iteration " + str(i)
        res = send_request("compile", helloDict)
        

test_hello_world()
