lines = open("test.txt").read().split("\n")
skeleton = open("skeleton.txt").read()

assemble = open("assemble.txt", "w")
for line in lines:
    if "// @judge-fill" in line:
        assemble.write("// begin user code\n%s\n// end user code\n" % skeleton)
    else:
        
        assemble.write("%s\n" % line)
