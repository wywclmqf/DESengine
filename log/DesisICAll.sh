#!/bin/bash

for i in 1 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log2/outputlocalICDesisTumTum$i
rm -rf ../log2/outputinterICDesisTumTum$i
rm -rf ../log2/outputrootICDesisTumTum$i
srun -A rabl -p magic -w node-12 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 6 3 > ../log2/outputlocalICDesisTumTum$i &

srun -A rabl -p magic -w node-11 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 6 3 &

srun -A rabl -p magic -w node-10 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 6 3 > ../log2/outputrootICDesisTumTum$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 1 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log2/outputlocalICDesisMedianMedian$i
rm -rf ../log2/outputinterICDesisMedianMedian$i
rm -rf ../log2/outputrootICDesisMedianMedian$i
srun -A rabl -p magic -w node-12 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 1 1 > ../log2/outputlocalICDesisMedianMedian$i &

srun -A rabl -p magic -w node-11 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 1 1 &

srun -A rabl -p magic -w node-10 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 1 1 > ../log2/outputrootICDesisMedianMedian$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log2/outputlocalICDesisMedianMax$i
rm -rf ../log2/outputinterICDesisMedianMax$i
rm -rf ../log2/outputrootICDesisMedianMax$i
srun -A rabl -p magic -w node-12 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 2 1 > ../log2/outputlocalICDesisMedianMax$i &

srun -A rabl -p magic -w node-11 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 2 1 &

srun -A rabl -p magic -w node-10 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 2 1 > ../log2/outputrootICDesisMedianMax$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log2/outputlocalICDesisMedianAverage$i
rm -rf ../log2/outputinterICDesisMedianAverage$i
rm -rf ../log2/outputrootICDesisMedianAverage$i
srun -A rabl -p magic -w node-12 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 3 1 > ../log2/outputlocalICDesisMedianAverage$i &

srun -A rabl -p magic -w node-11 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 3 1 &

srun -A rabl -p magic -w node-10 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 3 1 > ../log2/outputrootICDesisMedianAverage$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log2/outputlocalICDesisAverageMax$i
rm -rf ../log2/outputinterICDesisAverageMax$i
rm -rf ../log2/outputrootICDesisAverageMax$i
srun -A rabl -p magic -w node-12 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 4 3 > ../log2/outputlocalICDesisAverageMax$i &

srun -A rabl -p magic -w node-11 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 4 3 &

srun -A rabl -p magic -w node-10 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 4 3 > ../log2/outputrootICDesisAverageMax$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log2/outputlocalICDesisAverageSum$i
rm -rf ../log2/outputinterICDesisAverageSum$i
rm -rf ../log2/outputrootICDesisAverageSum$i
srun -A rabl -p magic -w node-12 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 5 3 > ../log2/outputlocalICDesisAverageSum$i &

srun -A rabl -p magic -w node-11 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 5 3 &

srun -A rabl -p magic -w node-10 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 5 3 > ../log2/outputrootICDesisAverageSum$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log2/outputlocalICDesisPunTum$i
rm -rf ../log2/outputinterICDesisPunTum$i
rm -rf ../log2/outputrootICDesisPunTum$i
srun -A rabl -p magic -w node-12 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 7 3 > ../log2/outputlocalICDesisPunTum$i &

srun -A rabl -p magic -w node-11 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 7 3 &

srun -A rabl -p magic -w node-10 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 7 3 > ../log2/outputrootICDesisPunTum$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log2/outputlocalICDesisPunPun$i
rm -rf ../log2/outputinterICDesisPunPun$i
rm -rf ../log2/outputrootICDesisPunPun$i
srun -A rabl -p magic -w node-12 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 8 3 > ../log2/outputlocalICDesisPunPun$i &

srun -A rabl -p magic -w node-11 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 8 3 &

srun -A rabl -p magic -w node-10 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 8 3 > ../log2/outputrootICDesisPunPun$i &

sleep 300

scancel -u wang.yue

sleep 20
done

echo END!!!



