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

rm -rf ../log2/outputlocalICDesisTumTum$i
rm -rf ../log2/outputinterICDesisTumTum$i
rm -rf ../log2/outputrootICDesisTumTum$i
srun -A rabl -p magic -w node-12 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 6 3 > ../log2/outputlocalICDesisTumTum$i &

srun -A rabl -p magic -w node-11 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 6 3 &

srun -A rabl -p magic -w node-10 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 6 3 > ../log2/outputrootICDesisTumTum$i &

rm -rf ../log1/outputlocalSWDesisTumTum$i
rm -rf ../log1/outputinterSWDesisTumTum$i
rm -rf ../log1/outputrootSWDesisTumTum$i
srun -A rabl -p magic -w node-06 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i 6 3 > ../log1/outputlocalSWDesisTumTum$i &

srun -A rabl -p magic -w node-05 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i 6 3 &

srun -A rabl -p magic -w node-04 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i 6 3 > ../log1/outputrootSWDesisTumTum$i &

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

rm -rf ../log2/outputlocalICDesisMedianMedian$i
rm -rf ../log2/outputinterICDesisMedianMedian$i
rm -rf ../log2/outputrootICDesisMedianMedian$i
srun -A rabl -p magic -w node-12 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 1 1 > ../log2/outputlocalICDesisMedianMedian$i &

srun -A rabl -p magic -w node-11 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 1 1 &

srun -A rabl -p magic -w node-10 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 1 1 > ../log2/outputrootICDesisMedianMedian$i &

rm -rf ../log1/outputlocalSWDesisMedianMedian$i
rm -rf ../log1/outputinterSWDesisMedianMedian$i
rm -rf ../log1/outputrootSWDesisMedianMedian$i
srun -A rabl -p magic -w node-06 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i 1 1 > ../log1/outputlocalSWDesisMedianMedian$i &

srun -A rabl -p magic -w node-05 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i 1 1 &

srun -A rabl -p magic -w node-04 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i 1 1 > ../log1/outputrootSWDesisMedianMedian$i &



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

rm -rf ../log2/outputlocalICDesisMedianMax$i
rm -rf ../log2/outputinterICDesisMedianMax$i
rm -rf ../log2/outputrootICDesisMedianMax$i
srun -A rabl -p magic -w node-12 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 2 1 > ../log2/outputlocalICDesisMedianMax$i &

srun -A rabl -p magic -w node-11 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 2 1 &

srun -A rabl -p magic -w node-10 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 2 1 > ../log2/outputrootICDesisMedianMax$i &

rm -rf ../log1/outputlocalSWDesisMedianMax$i
rm -rf ../log1/outputinterSWDesisMedianMax$i
rm -rf ../log1/outputrootSWDesisMedianMax$i
srun -A rabl -p magic -w node-06 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i 2 1 > ../log1/outputlocalSWDesisMedianMax$i &

srun -A rabl -p magic -w node-05 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i 2 1 &

srun -A rabl -p magic -w node-04 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i 2 1 > ../log1/outputrootSWDesisMedianMax$i &

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

rm -rf ../log2/outputlocalICDesisMedianAverage$i
rm -rf ../log2/outputinterICDesisMedianAverage$i
rm -rf ../log2/outputrootICDesisMedianAverage$i
srun -A rabl -p magic -w node-12 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 3 1 > ../log2/outputlocalICDesisMedianAverage$i &

srun -A rabl -p magic -w node-11 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 3 1 &

srun -A rabl -p magic -w node-10 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 3 1 > ../log2/outputrootICDesisMedianAverage$i &

rm -rf ../log1/outputlocalSWDesisMedianAverage$i
rm -rf ../log1/outputinterSWDesisMedianAverage$i
rm -rf ../log1/outputrootSWDesisMedianAverage$i
srun -A rabl -p magic -w node-06 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i 3 1 > ../log1/outputlocalSWDesisMedianAverage$i &

srun -A rabl -p magic -w node-05 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i 3 1 &

srun -A rabl -p magic -w node-04 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i 3 1 > ../log1/outputrootSWDesisMedianAverage$i &

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

rm -rf ../log2/outputlocalICDesisAverageMax$i
rm -rf ../log2/outputinterICDesisAverageMax$i
rm -rf ../log2/outputrootICDesisAverageMax$i
srun -A rabl -p magic -w node-12 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 4 3 > ../log2/outputlocalICDesisAverageMax$i &

srun -A rabl -p magic -w node-11 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 4 3 &

srun -A rabl -p magic -w node-10 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 4 3 > ../log2/outputrootICDesisAverageMax$i &

rm -rf ../log1/outputlocalSWDesisAverageMax$i
rm -rf ../log1/outputinterSWDesisAverageMax$i
rm -rf ../log1/outputrootSWDesisAverageMax$i
srun -A rabl -p magic -w node-06 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i 4 3 > ../log1/outputlocalSWDesisAverageMax$i &

srun -A rabl -p magic -w node-05 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i 4 3 &

srun -A rabl -p magic -w node-04 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i 4 3 > ../log1/outputrootSWDesisAverageMax$i &

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

rm -rf ../log2/outputlocalICDesisAverageSum$i
rm -rf ../log2/outputinterICDesisAverageSum$i
rm -rf ../log2/outputrootICDesisAverageSum$i
srun -A rabl -p magic -w node-12 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 5 3 > ../log2/outputlocalICDesisAverageSum$i &

srun -A rabl -p magic -w node-11 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 5 3 &

srun -A rabl -p magic -w node-10 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 5 3 > ../log2/outputrootICDesisAverageSum$i &

rm -rf ../log1/outputlocalSWDesisAverageSum$i
rm -rf ../log1/outputinterSWDesisAverageSum$i
rm -rf ../log1/outputrootSWDesisAverageSum$i
srun -A rabl -p magic -w node-06 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i 5 3 > ../log1/outputlocalSWDesisAverageSum$i &

srun -A rabl -p magic -w node-05 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i 5 3 &

srun -A rabl -p magic -w node-04 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i 5 3 > ../log1/outputrootSWDesisAverageSum$i &

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

rm -rf ../log2/outputlocalICDesisPunTum$i
rm -rf ../log2/outputinterICDesisPunTum$i
rm -rf ../log2/outputrootICDesisPunTum$i
srun -A rabl -p magic -w node-12 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 7 3 > ../log2/outputlocalICDesisPunTum$i &

srun -A rabl -p magic -w node-11 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 7 3 &

srun -A rabl -p magic -w node-10 --mem 40G 

rm -rf ../log1/outputlocalSWDesisPunTum$i
rm -rf ../log1/outputinterSWDesisPunTum$i
rm -rf ../log1/outputrootSWDesisPunTum$i
srun -A rabl -p magic -w node-06 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i 7 3 > ../log1/outputlocalSWDesisPunTum$i &

srun -A rabl -p magic -w node-05 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i 7 3 &

srun -A rabl -p magic -w node-04 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i 7 3 > ../log1/outputrootSWDesisPunTum$i &

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

rm -rf ../log2/outputlocalICDesisPunPun$i
rm -rf ../log2/outputinterICDesisPunPun$i
rm -rf ../log2/outputrootICDesisPunPun$i
srun -A rabl -p magic -w node-12 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 8 3 > ../log2/outputlocalICDesisPunPun$i &

srun -A rabl -p magic -w node-11 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 8 3 &

srun -A rabl -p magic -w node-10 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 8 3 > ../log2/outputrootICDesisPunPun$i &

rm -rf ../log1/outputlocalSWDesisPunPun$i
rm -rf ../log1/outputinterSWDesisPunPun$i
rm -rf ../log1/outputrootSWDesisPunPun$i
srun -A rabl -p magic -w node-06 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i 8 3 > ../log1/outputlocalSWDesisPunPun$i &

srun -A rabl -p magic -w node-05 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i 8 3 &

srun -A rabl -p magic -w node-04 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i 8 3 > ../log1/outputrootSWDesisPunPun$i &

sleep 300

scancel -u wang.yue

sleep 20
done

echo END!!!


