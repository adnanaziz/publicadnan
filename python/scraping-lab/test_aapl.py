import json
import solution_yahoo_options_data

computedJson = solution_yahoo_options_data.contractAsJson("aapl.dat")
expectedJson = open("aapl.json").read()

if json.loads(computedJson) != json.loads(expectedJson):
  print "Test failed!"
  print "Expected output:", expectedJson
  print "Your output:", computedJson
  assert False
else:
  print "Test passed"
