import json
import solution_yahoo_options_data

computedJson = solution_yahoo_options_data.contractAsJson("f.dat")
expectedJson = open("f.json").read()

if json.loads(computedJson) != json.loads(expectedJson):
  print "Test failed!"
  print "Expected output:", expectedJson
  print "Your output:", computedJson
  assert False
else:
  print "Test passed"
