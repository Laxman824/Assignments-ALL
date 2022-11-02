import os, filecmp

os.system("make compile")
os.system("make run")
r = filecmp.cmp("tests/output.txt", "tests/sample_out.txt")
if r is False:
    print("Fail")
else:
    print("Pass")
