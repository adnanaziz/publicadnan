# @judge-include-display')
lines = open("DutchNationalFlag.java").read().split("\n")

skeleton = open("skeleton.txt", "w")
test = open("test.txt", "w")

skeletonWrite = False
begin = -1
end = -1
lineNum = 1

for line in lines:
    if "// @judge-include-display" in line:
        if not skeletonWrite and begin == -1:
            begin = lineNum
        skeletonWrite = True
    if "// @judge-exclude-display" in line:
        skeletonWrite = False
        end = lineNum
    if skeletonWrite and not "// @judge-include-display" in line:
        skeleton.write("%s\n" % line)
    lineNum = lineNum + 1

print "begin, end = " + str(begin) + " " + str(end)

for i in range(0,begin):
    test.write("%s\n" % lines[i])

test.write("// @judge-fill")

for i in range(end+1,len(lines)):
    test.write("%s\n" % lines[i])
