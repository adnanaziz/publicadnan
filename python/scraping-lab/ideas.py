from bs4 import BeautifulSoup
import re

handle = open("f.dat")

rawText = handle.read()

soup = BeautifulSoup(rawText)

allLinks = soup.findAll('a')

for link in allLinks:
    if ( False and re.search("\/q\/op\?s.*m=", str(link)) ):
        print link

allSpecialLinks = soup.findAll(attrs={"class":"yfnc_h"})
for link in allSpecialLinks:
        print "link:", link
        #print "parent:", link.parent
        #print "parent's parent:", link.parent.parent



