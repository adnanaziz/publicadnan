#!/bin/tcsh
setenv LIST 'Parity1.java DutchNationalFlag.java InterconvertingStringInteger.java ConvertBase.java CheckingCycle.java StackWithMax.java BinaryTreeLevelOrder.java BalancedBinaryTree.java LinkedListPrototypeTemplate.java BinaryTreePrototypeTemplate.java BinarySearchTreePrototypeTemplate.java'
echo "here"

foreach f ( $LIST )
  echo "moving ~/epi/java/$f" 
  cp ~/epi/java/$f .
end
