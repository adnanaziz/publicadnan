import re

def skip( A, B ):
    fA = open(A)
    fB = open(B, "w")

    printLine = True
    for line in fA:
        if re.match("@skip-begin", line):
            printLine = False   
            continue
        if re.match("@skip-end", line):
            printLine = True
            continue
        if printLine:
            fB.write(line)
   
skip("input.txt", "output.txt")
