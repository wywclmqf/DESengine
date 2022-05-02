#!/bin/bash

for i in 1 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalQuantileDesisMedianMedian$i
rm -rf ../log/outputinterQuantileDesisMedianMedian$i
rm -rf ../log/outputrootQuantileDesisMedianMedian$i
srun -A rabl -p magic -w node-07 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisQuantile.EntryPoint.EntryPointForLocalNode 4 $i 1 1 > ../log/outputlocalQuantileDesisMedianMedian$i &

srun -A rabl -p magic -w node-09 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisQuantile.EntryPoint.EnteryPointForIntermediaNode 2 $i 1 1 > ../log/outputinterQuantileDesisMedianMedian$i &

srun -A rabl -p magic -w node-08 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisQuantile.EntryPoint.EnteryPointForRootnode 1 $i 1 1 > ../log/outputrootQuantileDesisMedianMedian$i &

sleep 300

sleep 60
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalQuantileDesisMedianMax$i
rm -rf ../log/outputinterQuantileDesisMedianMax$i
rm -rf ../log/outputrootQuantileDesisMedianMax$i
srun -A rabl -p magic -w node-07 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisQuantile.EntryPoint.EntryPointForLocalNode 4 $i 2 1 > ../log/outputlocalQuantileDesisMedianMax$i &

srun -A rabl -p magic -w node-09 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisQuantile.EntryPoint.EnteryPointForIntermediaNode 2 $i 2 1 > ../log/outputinterQuantileDesisMedianMax$i &

srun -A rabl -p magic -w node-08 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisQuantile.EntryPoint.EnteryPointForRootnode 1 $i 2 1 > ../log/outputrootQuantileDesisMedianMax$i &

sleep 300

sleep 60
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalQuantileDesisMedianAverage$i
rm -rf ../log/outputinterQuantileDesisMedianAverage$i
rm -rf ../log/outputrootQuantileDesisMedianAverage$i
srun -A rabl -p magic -w node-07 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisQuantile.EntryPoint.EntryPointForLocalNode 4 $i 3 1 > ../log/outputlocalQuantileDesisMedianAverage$i &

srun -A rabl -p magic -w node-09 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisQuantile.EntryPoint.EnteryPointForIntermediaNode 2 $i 3 1 > ../log/outputinterQuantileDesisMedianAverage$i &

srun -A rabl -p magic -w node-08 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisQuantile.EntryPoint.EnteryPointForRootnode 1 $i 3 1 > ../log/outputrootQuantileDesisMedianAverage$i &

sleep 300

sleep 60
done


echo END!!!

