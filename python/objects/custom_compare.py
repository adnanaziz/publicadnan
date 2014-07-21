def custom_compare(x,y):
    if (x[1] != y[1]): 
        return x[1] - y[1]
    else:
        return cmp(x[0], y[0]) 
  
A = [("aa", 100), ("A", 100), ("adnan",10), ("aa", 1000), ("b",5)]
A.sort(custom_compare)
print A
