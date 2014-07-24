from urllib import urlopen
 
#optionsUrl = 'http://finance.yahoo.com/q/op?s=AAPL+Options'
#optionsPage = urlopen(optionsUrl)

#s = optionsPage.read()
#print s
#
#AAPLhandle = open("aapl.dat", "w")
#
#AAPLhandle.write(s)

AAPLhandle = open("aapl.dat")
optionsPageText = AAPLhandle.read()

#print optionsPageText

from bs4 import BeautifulSoup

#soup = BeautifulSoup(optionsPage)
soupText = BeautifulSoup(optionsPageText)

#print soup.prettify()
#print soup.find_all('<a>')

anchor = "AAPL140725C00086430"

#print soup.findAll(text=anchor)
#print soupText.findAll(text=anchor)
#print soupText.findAll(text=anchor)[0].parent
#print soupText.findAll(text=anchor)[0].parent.parent
#print soupText.findAll(text=anchor)[0].parent.parent.parent


optionsTable = [
    [x.text for x in y.parent.contents]
    for y in soupText.findAll('td', attrs={'class': 'yfnc_h', 'nowrap': ''})
]

for y in soupText.findAll('td', attrs={'class': 'yfnc_h', 'nowrap': ''}):
    print "y = ", y
    for x in y.parent.contents:
        print "x.text = ", x.text

#for item in optionsTable:
    # print item
    #if len(item) == 8 and int(item[7].replace(',', '')) > 1000:
        #print item
        # print float(float(item[5]) - float(item[4]))/(float(item[5]) + float(item[4])), item[0], item [1], item[4], item[5]
