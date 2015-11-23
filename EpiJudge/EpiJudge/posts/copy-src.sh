#!/bin/tcsh
setenv LIST 'Parity1.java DutchNationalFlag.java InterconvertingStringInteger.java ConvertBase.java CheckingCycle.java StackWithMax.java BinaryTreeLevelOrder.java BalancedBinaryTree.java MergeSortedArrays.java BinarySearchFirstK.java AnonymousLetter.java IntersectSortedArrays3.java RenderingCalendar.java IsBinaryTreeABST.java  PowerSet.java NumberWays.java ThreeSum.java PaintingIterative.java GCD1.java BiggestProductNMinus1.java LinkedListPrototypeTemplate.java BinaryTreePrototypeTemplate.java BinarySearchTreePrototypeTemplate.java'

echo "here"

foreach f ( $LIST )
  echo "copying ~/epi/java/$f" 
  cp ~/epi/java/$f .
end
