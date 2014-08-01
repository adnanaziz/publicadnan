# fname = raw_input('Enter the file name: ')
fname = "romeo.txt"
try:
    fhand = open(fname)
except:
    print 'File cannot be opened:', fname
    exit()

counts = dict()

for line in fhand:
  words = line.split()
  for w in words:
    counts[w] = counts.get(w, 0) + 1

all_words = counts.keys()
all_words.sort()
for w in all_words:
  print w, counts[w]
    
