#!/bin/bash

for i in 1000 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputSWlocal$i
rm -rf ../log/outputSWinter$i
rm -rf ../log/outputSWroot$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../Desis.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputSWlocal$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i > ../log/outputSWinter$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputSWroot$i &

sleep 300

scancel -u wang.yue

sleep 20
done

echo END!!!
