#!/bin/bash

for i in 1 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalDesisTumTum$i
rm -rf ../log/outputinterDesisTumTum$i
rm -rf ../log/outputrootDesisTumTum$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 $i 6 3 > ../log/outputlocalDesisTumTum$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 $i 6 3 > ../log/outputinterDesisTumTum$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 $i 6 3 > ../log/outputrootDesisTumTum$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 1 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalDesisMedianMedian$i
rm -rf ../log/outputinterDesisMedianMedian$i
rm -rf ../log/outputrootDesisMedianMedian$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 $i 1 1 > ../log/outputlocalDesisMedianMedian$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 $i 1 1 > ../log/outputinterDesisMedianMedian$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 $i 1 1 > ../log/outputrootDesisMedianMedian$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalDesisMedianMax$i
rm -rf ../log/outputinterDesisMedianMax$i
rm -rf ../log/outputrootDesisMedianMax$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 $i 2 1 > ../log/outputlocalDesisMedianMax$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 $i 2 1 > ../log/outputinterDesisMedianMax$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 $i 2 1 > ../log/outputrootDesisMedianMax$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalDesisMedianAverage$i
rm -rf ../log/outputinterDesisMedianAverage$i
rm -rf ../log/outputrootDesisMedianAverage$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 $i 3 1 > ../log/outputlocalDesisMedianAverage$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 $i 3 1 > ../log/outputinterDesisMedianAverage$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 $i 3 1 > ../log/outputrootDesisMedianAverage$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalDesisAverageMax$i
rm -rf ../log/outputinterDesisAverageMax$i
rm -rf ../log/outputrootDesisAverageMax$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 $i 4 3 > ../log/outputlocalDesisAverageMax$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 $i 4 3 > ../log/outputinterDesisAverageMax$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 $i 4 3 > ../log/outputrootDesisAverageMax$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalDesisAverageSum$i
rm -rf ../log/outputinterDesisAverageSum$i
rm -rf ../log/outputrootDesisAverageSum$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 $i 5 3 > ../log/outputlocalDesisAverageSum$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 $i 5 3 > ../log/outputinterDesisAverageSum$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 $i 5 3 > ../log/outputrootDesisAverageSum$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalDesisPunTum$i
rm -rf ../log/outputinterDesisPunTum$i
rm -rf ../log/outputrootDesisPunTum$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 $i 7 3 > ../log/outputlocalDesisPunTum$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 $i 7 3 > ../log/outputinterDesisPunTum$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 $i 7 3 > ../log/outputrootDesisPunTum$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalDesisPunPun$i
rm -rf ../log/outputinterDesisPunPun$i
rm -rf ../log/outputrootDesisPunPun$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 $i 8 3 > ../log/outputlocalDesisPunPun$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 $i 8 3 > ../log/outputinterDesisPunPun$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 $i 8 3 > ../log/outputrootDesisPunPun$i &

sleep 300

scancel -u wang.yue

sleep 20
done

echo END!!!

