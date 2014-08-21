# from Budd's "Exploring Python":

# define a class to simulate a bank account
class BankAccount(object):

  # no multiple constructors, but can use default arguments
  def __init__ (self, balance=0):
    # init balance to zero
    self.balance = balance

  def deposit(self, amount):
    # add amount to balance
    self.balance += amount

  def withdraw(self, amount):
    if self.balance < amount:
      raise ValueError, "NSF"
    self.balance -= amount

  def getBalance(self):
    return self.balance

  def transfer(self, amount, toAccount):
    # move cash from this account to toAccount, highly thread-unsafe
    self.withdraw(amount)
    toAccount.deposit(amount)

  def __str__(self):
    return 'Account balance = ' + str(self.balance)

class CheckingAccount(BankAccount):

  def __init__(self, initBal):
    BankAccount.__init__(self, initBal)
    #BankAccount.__init__(self)
    self.checkRecord = {}

  def processCheck(self, number, toWho, amount):
    self.withdraw(amount)
    self.checkRecord[number] = (toWho, amount)

  def checkInfo(self, number):
    if number in self.checkRecord:
      return self.checkRecord[number]
    else:
      return 'no such check'
  
  # Illustrates overriding a method:
  def withdraw(self, amount):
    print "withdrawing:", amount
    BankAccount.withdraw(self, amount)


acct0 = BankAccount()
acct1 = BankAccount()

acct0.deposit(120)
acct0.withdraw(25)

acct1.deposit(350)
acct1.withdraw(20)

print acct0.getBalance()
print acct1.getBalance()

print acct0

print acct0.__class__
print BankAccount.__name__
print acct0.__dict__

ca = CheckingAccount(300)
print ca
ca.processCheck(100, "PGE", 72.50)
ca.processCheck(102, "ATT", 178.91)
print ca.checkInfo(100)

ca.deposit(50)

print ca.getBalance()

try:
  ca.withdraw(600)
  print "money for nothing!"
except ValueError, e:
  print "no such luck"
  print "error msg is", e


