#!/bin/bash

for i in 2 10 50 100 500 1000
do

echo $i 
rm -rf ../log2/outputlocalICDesisPunTum$i
rm -rf ../log2/outputinterICDesisPunTum$i
rm -rf ../log2/outputrootICDesisPunTum$i
srun -A rabl -p magic -w node-12 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 7 3 > ../log2/outputlocalICDesisPunTum$i &

srun -A rabl -p magic -w node-11 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 7 3 &

srun -A rabl -p magic -w node-10 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 7 3 > ../log2/outputrootICDesisPunTum$i &

sleep 300

sleep 60

done

echo END!!!
