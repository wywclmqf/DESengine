#!/bin/bash

for i in 1 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalCenDesisTumTum$i
rm -rf ../log/outputrootCenDesisTumTum$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisTumTum.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputlocalCenDesisTumTum$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisTumTum.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputrootCenDesisTumTum$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 1 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalCenDesisMedianMedian$i
rm -rf ../log/outputrootCenDesisMedianMedian$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisMedianMedian.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputlocalCenDesisMedianMedian$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisMedianMedian.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputrootCenDesisMedianMedian$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalCenDesisMedianMax$i
rm -rf ../log/outputrootCenDesisMedianMax$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisMedianMax.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputlocalCenDesisMedianMax$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisMedianMax.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputrootCenDesisMedianMax$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalCenDesisMedianAverage$i
rm -rf ../log/outputrootCenDesisMedianAverage$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisMedianAverage.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputlocalCenDesisMedianAverage$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisMedianAverage.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputrootCenDesisMedianAverage$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalCenDesisAverageMax$i
rm -rf ../log/outputrootCenDesisAverageMax$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisAverageMax.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputlocalCenDesisAverageMax$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisAverageMax.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputrootCenDesisAverageMax$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalCenDesisAverageSum$i
rm -rf ../log/outputrootCenDesisAverageSum$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisAverageSum.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputlocalCenDesisAverageSum$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisAverageSum.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputrootCenDesisAverageSum$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalCenDesisPunTum$i
rm -rf ../log/outputrootCenDesisPunTum$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisPunTum.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputlocalCenDesisPunTum$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisPunTum.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputrootCenDesisPunTum$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalCenDesisPunPun$i
rm -rf ../log/outputrootCenDesisPunPun$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisPunPun.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputlocalCenDesisPunPun$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisPunPun.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputrootCenDesisPunPun$i &

sleep 300

scancel -u wang.yue

sleep 20
done

echo END!!!
