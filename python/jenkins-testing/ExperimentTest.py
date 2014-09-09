import unittest
import Experiment
 
class MyTestCase(unittest.TestCase):

  def test_t1(self):
    r1 = Experiment.largest([1,1,1]) 
    self.assertEqual(r1, 1)

  def test_t2(self):
    r1 = Experiment.largest([3,2,1]) 
    self.assertEqual(r1, 3)

  def test_t3(self):
    r1 = Experiment.largest([3,2,3]) 
    self.assertEqual(r1, 3)
 
 
if __name__ == '__main__':
    unittest.main()
