import json
import yahoo_options_data

computedJson = yahoo_options_data.contractAsJson("f.dat")
expectedJson = open("f.json").read()
expectedJson_change = open("f_change.json").read()

if json.loads(computedJson) != json.loads(expectedJson) and json.loads(computedJson) != json.loads(expectedJson_change):
  print "Test failed!"
  print "Expected output:", expectedJson
  print "Your output:", computedJson
  assert False
else:
  print "Test passed"
