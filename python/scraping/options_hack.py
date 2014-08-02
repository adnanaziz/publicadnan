import json
import sys
import re
from urllib import urlopen

# to get data programmatically:
#optionsUrl = 'http://finance.yahoo.com/q/op?s=AAPL+Options'
#optionsPage = urlopen(optionsUrl)
#s = optionsPage.read()
#print s
#
#fhandle = open("aapl.dat", "w")
#
#fhandle.write(s)

# AAPL140725C00080000
def contract(item):
  item_string = str(item)
  #print "item_string", item_string
  contract_symbol = re.findall(r"([A-Z]+)\d.*[CP]", item_string)
  #print "contract_symbol", contract_symbol
  if len(contract_symbol) == 0:
    #print "empty contract_symbol, item is ", item_string
    1 + 1 # to indent
  # print "contract_symbol", contract_symbol[0]
  # 140725
  contract_date = re.findall("[A-Z]+(\d+)[CP]", item_string )
  #print "contract_date", contract_date[0]
  contract_type =  re.findall("[A-Z]+\d+([CP])", item_string)
  #print "contract_type", contract_type[0]
  constract_price = re.findall("[A-Z]+\d+[CP](\d+)", item_string)
  #print "constract_price", float(int(constract_price[0]))/10.0
  return {"Symbol":contract_symbol[0], \
          "Date": contract_date[0], \
          "Type": contract_type[0], \
          "Strike": float(int(constract_price[0]))/10.0 }


def contractAsJson(filename):

  filehandle = open(filename)
  optionsPageText = filehandle.read()
  
  #print optionsPageText
  
  from bs4 import BeautifulSoup
  #print BeautifulSoup
  
  #soup = BeautifulSoup(optionsPage)
  soupText = BeautifulSoup(optionsPageText)
  
  import re
  # select vs findall???
  strike_dates = soupText.select('a')  # re.compile('.*')})
  #print "strike_dates", str(strike_dates)
  dateUrls = []
  #print "start dates"
  for kid in strike_dates:
    #print "kid:", kid
    if re.search(r"s=[A-Z]+&amp;m=", str(kid)):
      aDate = re.findall("[\"](.*)[\"]", str(kid))
      dateUrls.append("http://finance.yahoo.com" + aDate[0])
  #print "end dates"
  #print dateUrls
  
  
  curr_price_bs = soupText.findAll(attrs={'class':'time_rtq_ticker'})
  currPrice = 0.0
  #print "curr_price:", str(curr_price_bs)
  for kid in curr_price_bs[0]:
    #print "kid = ", kid
    price = re.findall("\d+\.\d+", str(kid))
    #print "current price:", float(price[0])
    currPrice = float(price[0])
  
  
  #print soup.prettify()
  #print soup.find_all('<a>')
  
  anchor = "AAPL140725C00086430"
  
  #print soup.findAll(text=anchor)
  #print soupText.findAll(text=anchor)
  #print soupText.findAll(text=anchor)[0].parent
  #print soupText.findAll(text=anchor)[0].parent.parent
  #print soupText.findAll(text=anchor)[0].parent.parent.parent
  
  
  #optionsTable = [
  #    [x.text for x in y.parent.contents]
  #    for y in soupText.findAll('td', attrs={'class': 'yfnc_h', 'nowrap': ''})
  #]
  
  #for y in soupText.findAll('td', attrs={'class': "yfnc_h", 'nowrap':""}):
  optionsTable = []
  for y in soupText.findAll( attrs={'class': "yfnc_h", 'nowrap': ''}):
      if ( "nowrap" in str(y) ):
        # print "y = ", y
        optionsTable.append( [x.text for x in y.parent.contents] )
  #     for x in y.parent.contents:
  #         print "x.text = ", x.text
  for y in soupText.findAll( attrs={'class': "yfnc_tabledata1", 'nowrap': ''}):
      if ( "nowrap" in str(y) ):
        # print "y = ", y
        optionsTable.append( [x.text for x in y.parent.contents] )
  #     for x in y.parent.contents:
 

  # bring calls together 
  optionsTable.sort(lambda x, y: -1 if re.findall("[A-Z]+\d+([CP])", str(x))[0] == "C" and re.findall("[A-Z]+\d+([CP])", str(y))[0] == "P" \
                            else 1 if re.findall("[A-Z]+\d+([CP])", str(x))[0] == "P" and re.findall("[A-Z]+\d+([CP])", str(y))[0] == "C" else 0)

  # item is [u'81.43', u'AAPL140725C00081430', u'13.17', u' 0.00', u'12.70', u'14.40', u'2', u'72']
  optionQuotes = []
  for item in optionsTable:
    #print "an item:", item
    if len(item) == 8:
      data = contract(item)
      data["Strike"] = item[0]
      data["Last"] = item[2]
      data["Change"] = item[3]
      data["Bid"] = item[4]
      data["Ask"] = item[5]
      data["Vol"] = item[6]
      data["Open"] = item[7]
      #data["ImpliedVolatility"] = ImpliedVolatility(CallPutFlag,S,X,T,r,p)

      #print data
      #print "adding data:", data
      optionQuotes.append(data)
      # print item
      # print float(float(item[5]) - float(item[4]))/(float(item[5]) + float(item[4])), item[0], item [1], item[4], item[5]

  optionQuotes.sort(lambda x, y: 1 if (int(x["Open"].replace(",", "")) < int(y["Open"].replace(",", "" ))) \
                            else -1 if (int(x["Open"].replace(",", "")) > int(y["Open"].replace(",", "" ))) else 0 )
  
  jsonQuoteData = json.dumps({"currPrice":currPrice, "dateUrls":dateUrls, "optionQuotes":optionQuotes}, sort_keys=True, indent=4, separators=(',', ': '))
  
  return jsonQuoteData

print contractAsJson(sys.argv[1])

#sys.exit()
