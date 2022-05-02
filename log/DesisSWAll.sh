#!/bin/bash

for i in 1 2 10 50 100 500 1000
do

echo $i	
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


