'''
Tests EpiJudge backend

@author: Adnan
'''

import json
import re
import os
import time
import requests  

import random

EPI_JUDGE_SERVER = '146.6.28.101'
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
        self.url = "http://" + self.server + ":" +  str(self.port) + "/"


S = server()

def send_request(service, req, verbose=True):
    url = S.url + service
    reqString = json.dumps(req)
    print "Requesting service at " + bcolors.OKGREEN + url + bcolors.ENDC
    print bcolors.HEADER + "\n>>> Request JSON = \n" + bcolors.ENDC + prettyprint_json(req)
    res = requests.post(url, data=req)
    jsonres = json.loads(res.text)
    if verbose:
        jsonrestext =  prettyprint_json(jsonres)
    else:
        jsonrestext =  prettyprint_json(jsonres)[:20]
    print bcolors.HEADER + "\n>>> Response JSON = \n" + bcolors.ENDC + jsonrestext + "\n\n"
    return jsonres


# programs to run on compilebox:
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
    res = send_request("compile", helloDict)

    helloDict["code"] = FAIL_COMPILE_1
    res = send_request("compile", helloDict)

    helloDict["code"] = FAIL_RUN_1
    res = send_request("compile", helloDict)

    helloDict["code"] = FAIL_RUN_2
    res = send_request("compile", helloDict)

    helloDict["code"] = FAIL_RUN_3
    res = send_request("compile", helloDict)

    helloDict["code"] = HELLO_WORLD
    for i in range(5):
        print "Iteration " + str(i)
        res = send_request("compile", helloDict)
        
test_hello_world()
