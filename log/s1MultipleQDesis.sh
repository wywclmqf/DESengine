#!/bin/bash

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocal$i
rm -rf ../log/outputinter$i
rm -rf ../log/outputroot$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../Desis.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputlocal$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 $i > ../log/outputinter$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputroot$i &

sleep 300

scancel -u wang.yue

sleep 20
done

echo END!!!
