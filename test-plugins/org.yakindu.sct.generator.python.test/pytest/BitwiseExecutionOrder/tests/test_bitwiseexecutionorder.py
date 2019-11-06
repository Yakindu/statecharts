
import unittest

import sys, os
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))
from bitwiseexecutionorder.bitwiseexecutionorder_statemachine import BitwiseExecutionOrder
from tests.vtimer.virtual_timer import *

# Unit TestCase for BitwiseExecutionOrder
class BitwiseexecutionorderTestCase(unittest.TestCase):
	
	
	def setUp(self):
		self.statemachine = BitwiseExecutionOrder()
		self.timer = VirtualTimer(200)
		self.timer.schedule_periodical_task(CycleTimeEventTask(self.statemachine), 200, 200)
		self.statemachine.init()

	def tearDown(self):
		self.timer.stop()
		self.statemachine.exit()
	
	def test_test(self):
		self.statemachine.enter()
		self.timer.cycle_leap(1)
		self.assertTrue(self.statemachine.is_final())
		self.statemachine.exit()
		
