#!/bin/bash

for i in 1 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputSWlocalDesisAverage$i
rm -rf ../log/outputSWinterDesisAverage$i
rm -rf ../log/outputSWrootDesisAverage$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisAverage.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputSWlocalDesisAverage$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisAverage.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i > ../log/outputSWinterDesisAverage$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisAverage.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputSWrootDesisAverage$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 1 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputSWlocalDesisMedian$i
rm -rf ../log/outputSWinterDesisMedian$i
rm -rf ../log/outputSWrootDesisMedian$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisMedian.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputSWlocalDesisMedian$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisMedian.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i > ../log/outputSWinterDesisMedian$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisMedian.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputSWrootDesisMedian$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputSWlocalDesisMaxMedian$i
rm -rf ../log/outputSWinterDesisMaxMedian$i
rm -rf ../log/outputSWrootDesisMaxMedian$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisMaxMedian.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputSWlocalDesisMaxMedian$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisMaxMedian.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i > ../log/outputSWinterDesisMaxMedian$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisMaxMedian.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputSWrootDesisMaxMedian$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputSWlocalDesisSumMedian$i
rm -rf ../log/outputSWinterDesisSumMedian$i
rm -rf ../log/outputSWrootDesisSumMedian$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisSumMedian.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputSWlocalDesisSumMedian$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisSumMedian.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i > ../log/outputSWinterDesisSumMedian$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisSumMedian.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputSWrootDesisSumMedian$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputSWlocalDesisSumMax$i
rm -rf ../log/outputSWinterDesisSumMax$i
rm -rf ../log/outputSWrootDesisSumMax$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisSumMax.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputSWlocalDesisSumMax$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisSumMax.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i > ../log/outputSWinterDesisSumMax$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisSumMax.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputSWrootDesisSumMax$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputSWlocalDesisAverageSum$i
rm -rf ../log/outputSWinterDesisAverageSum$i
rm -rf ../log/outputSWrootDesisAverageSum$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisAverageSum.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputSWlocalDesisAverageSum$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisAverageSum.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i > ../log/outputSWinterDesisAverageSum$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisAverageSum.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputSWrootDesisAverageSum$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputSWlocalDesisTumPun$i
rm -rf ../log/outputSWinterDesisTumPun$i
rm -rf ../log/outputSWrootDesisTumPun$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisTumPun.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputSWlocalDesisTumPun$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisTumPun.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i > ../log/outputSWinterDesisTumPun$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisTumPun.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputSWrootDesisTumPun$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputSWlocalDesisPunPun$i
rm -rf ../log/outputSWinterDesisPunPun$i
rm -rf ../log/outputSWrootDesisPunPun$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisPunPun.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputSWlocalDesisPunPun$i &

srun -A rabl -p magic -w node-09 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisPunPun.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i > ../log/outputSWinterDesisPunPun$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisPunPun.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputSWrootDesisPunPun$i &

sleep 300

scancel -u wang.yue

sleep 20
done

echo END!!!
