# from Budd's "Exploring Python":

class BankAccount(object):
  # define a class to simulate a bank account
  def __init__ (self):
    # init balance to zero
    self.balance = 0
  def deposit(self, amount):
    # add amount to balance
    self.balance += amount
  def withdraw(self, amount):
    self.balance -= amount
  def getBalance(self):
    return self.balance

acct0 = BankAccount()
acct1 = BankAccount()

acct0.deposit(120)
acct0.withdraw(25)

acct1.deposit(350)
acct1.withdraw(20)

print acct0.getBalance()
print acct1.getBalance()
