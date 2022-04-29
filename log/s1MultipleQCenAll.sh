#!/bin/bash

for i in 1 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputCenlocalDesisAverage$i
rm -rf ../log/outputCeninterDesisAverage$i
rm -rf ../log/outputCenrootDesisAverage$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisAverage.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputCenlocalDesisAverage$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisAverage.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputCenrootDesisAverage$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 1 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputCenlocalDesisMedian$i
rm -rf ../log/outputCeninterDesisMedian$i
rm -rf ../log/outputCenrootDesisMedian$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisMedian.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputCenlocalDesisMedian$i &


srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisMedian.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputCenrootDesisMedian$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputCenlocalDesisMaxMedian$i
rm -rf ../log/outputCeninterDesisMaxMedian$i
rm -rf ../log/outputCenrootDesisMaxMedian$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisMaxMedian.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputCenlocalDesisMaxMedian$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisMaxMedian.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputCenrootDesisMaxMedian$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputCenlocalDesisSumMedian$i
rm -rf ../log/outputCeninterDesisSumMedian$i
rm -rf ../log/outputCenrootDesisSumMedian$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisSumMedian.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputCenlocalDesisSumMedian$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisSumMedian.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputCenrootDesisSumMedian$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputCenlocalDesisSumMax$i
rm -rf ../log/outputCeninterDesisSumMax$i
rm -rf ../log/outputCenrootDesisSumMax$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisSumMax.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputCenlocalDesisSumMax$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisSumMax.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputCenrootDesisSumMax$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputCenlocalDesisAverageSum$i
rm -rf ../log/outputCeninterDesisAverageSum$i
rm -rf ../log/outputCenrootDesisAverageSum$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisAverageSum.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputCenlocalDesisAverageSum$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisAverageSum.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputCenrootDesisAverageSum$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputCenlocalDesisTumPun$i
rm -rf ../log/outputCeninterDesisTumPun$i
rm -rf ../log/outputCenrootDesisTumPun$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisTumPun.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputCenlocalDesisTumPun$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisTumPun.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputCenrootDesisTumPun$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputCenlocalDesisPunPun$i
rm -rf ../log/outputCeninterDesisPunPun$i
rm -rf ../log/outputCenrootDesisPunPun$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisPunPun.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputCenlocalDesisPunPun$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisPunPun.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputCenrootDesisPunPun$i &

sleep 300

scancel -u wang.yue

sleep 20
done

echo END!!!
