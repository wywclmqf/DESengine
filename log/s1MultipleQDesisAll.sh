#!/bin/bash

for i in 2 10 50 100 500 1000
do

echo $i 
rm -rf ../log/outputlocalDesisSumMax$i
rm -rf ../log/outputinterDesisSumMax$i
rm -rf ../log/outputrootDesisSumMax$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisSumMax.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputlocalDesisSumMax$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisSumMax.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 $i > ../log/outputinterDesisSumMax$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisSumMax.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputrootDesisSumMax$i &

sleep 300

scancel -u wang.yue

sleep 30
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalDesisMaxMedian$i
rm -rf ../log/outputinterDesisMaxMedian$i
rm -rf ../log/outputrootDesisMaxMedian$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisMaxMedian.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputlocalDesisMaxMedian$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisMaxMedian.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 $i > ../log/outputinterDesisMaxMedian$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisMaxMedian.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputrootDesisMaxMedian$i &

sleep 300

scancel -u wang.yue

sleep 30
done


for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalDesisSumMedian$i
rm -rf ../log/outputinterDesisSumMedian$i
rm -rf ../log/outputrootDesisSumMedian$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisSumMedian.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputlocalDesisSumMedian$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisSumMedian.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 $i > ../log/outputinterDesisSumMedian$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisSumMedian.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputrootDesisSumMedian$i &

sleep 300

scancel -u wang.yue

sleep 30
done


echo END!!!
