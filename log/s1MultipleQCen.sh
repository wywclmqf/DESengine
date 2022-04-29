#!/bin/bash

for i in 1 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputCenlocal$i
rm -rf ../log/outputCenroot$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../Desis.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputCenlocal$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../Desis.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputCenrot$i &

sleep 300

scancel -u wang.yue

sleep 20
done

echo END!!!
