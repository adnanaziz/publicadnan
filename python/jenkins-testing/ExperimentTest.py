import unittest
import Experiment
 
class MyTestCase(unittest.TestCase):

  def test_t1(self):
    r1 = Experiment.largest([1,1,1]) 
    self.assertEqual(r1, 1)
 
if __name__ == '__main__':
    unittest.main()
