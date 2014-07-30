import re

def skip( A, B ):
    fA = open(A)
    fB = open(B, "w")

    printLine = True
    for line in fA:
        if re.match("@skip-begin", line):
            while not re.match("@skip-end", fA.next() ):
                continue
        else:
            print line
            fB.write(line)
   
skip("input.txt", "output.txt")
