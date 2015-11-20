#!/bin/tcsh
setenv LIST 'Parity1.java DutchNationalFlag.java InterconvertingStringInteger.java ConvertBase.java CheckingCycle.java StackWithMax.java BinaryTreeLevelOrder.java BalancedBinaryTree.java MergeSortedArrays.java BinarySearchFirstK.java AnonymousLetter.java IntersectSortedArrays3.java RenderingCalendar.java LinkedListPrototypeTemplate.java BinaryTreePrototypeTemplate.java BinarySearchTreePrototypeTemplate.java'

echo "here"

foreach f ( $LIST )
  echo "moving ~/epi/java/$f" 
  cp ~/epi/java/$f .
end
