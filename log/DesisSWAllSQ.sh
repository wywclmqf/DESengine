#!/bin/bash

rm -rf ../logDesisSW/output*
echo "Tmbling"
srun -A rabl -p magic -w node-14 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 1 11 3 > ../logDesisSW/outputlocalSWDesisTumbling &

srun -A rabl -p magic -w node-13 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 1 11 3 &

srun -A rabl -p magic -w node-02 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 1 11 3 > ../logDesisSW/outputrootSWDesisTumbling &

sleep 300
sleep 60

echo "Slding"
srun -A rabl -p magic -w node-14 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 1 12 3 > ../logDesisSW/outputlocalSWDesisSliding &

srun -A rabl -p magic -w node-13 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 1 12 3 &

srun -A rabl -p magic -w node-02 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 1 12 3 > ../logDesisSW/outputrootSWDesisSliding &

sleep 300
sleep 60

echo "Punctuation"
srun -A rabl -p magic -w node-14 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 1 13 3 > ../logDesisSW/outputlocalSWDesisPunctuation &

srun -A rabl -p magic -w node-13 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 1 13 3 &

srun -A rabl -p magic -w node-02 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 13 6 3 > ../logDesisSW/outputrootSWDesisPunctuation &

sleep 300
sleep 60

echo "Session"
srun -A rabl -p magic -w node-14 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 1 14 3 > ../logDesisSW/outputlocalSWDesisSession &

srun -A rabl -p magic -w node-13 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 1 14 3 &

srun -A rabl -p magic -w node-02 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 1 14 3 > ../logDesisSW/outputrootSWDesisSession &

sleep 300
sleep 60

echo "Counting"
srun -A rabl -p magic -w node-14 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 1 15 3 > ../logDesisSW/outputlocalSWDesisCounting &

srun -A rabl -p magic -w node-13 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 1 15 3 &

srun -A rabl -p magic -w node-02 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 1 15 3 > ../logDesisSW/outputrootSWDesisCounting &

sleep 300
sleep 60




echo "Median"
srun -A rabl -p magic -w node-14 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 1 21 1 > ../logDesisSW/outputlocalSWDesisMedian &

srun -A rabl -p magic -w node-13 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 1 21 1 &

srun -A rabl -p magic -w node-02 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 1 21 1 > ../logDesisSW/outputrootSWDesisMedian &

sleep 300
sleep 60

echo "Quantile"
srun -A rabl -p magic -w node-14 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 1 22 1 > ../logDesisSW/outputlocalSWDesisQuantile &

srun -A rabl -p magic -w node-13 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 1 22 1 &

srun -A rabl -p magic -w node-02 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 1 22 1 > ../logDesisSW/outputrootSWDesisQuantile &

sleep 300
sleep 60

echo "Average"
srun -A rabl -p magic -w node-14 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 1 23 3 > ../logDesisSW/outputlocalSWDesisAverage &

srun -A rabl -p magic -w node-13 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 1 23 3 &

srun -A rabl -p magic -w node-02 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 1 23 3 > ../logDesisSW/outputrootSWDesisAverage &

sleep 300
sleep 60

echo "Sum"
srun -A rabl -p magic -w node-14 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 1 24 3 > ../logDesisSW/outputlocalSWDesisSum &

srun -A rabl -p magic -w node-13 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 1 24 3  &

srun -A rabl -p magic -w node-02 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 1 24 3 > ../logDesisSW/outputrootSWDesisSum &

sleep 300
sleep 60

echo "Count"
srun -A rabl -p magic -w node-14 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 1 25 3 > ../logDesisSW/outputlocalSWDesisCount &

srun -A rabl -p magic -w node-13 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 1 25 3 &

srun -A rabl -p magic -w node-02 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 1 25 3 > ../logDesisSW/outputrootSWDesisCount &

sleep 300
sleep 60


echo "Max"
srun -A rabl -p magic -w node-14 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 1 26 3 > ../logDesisSW/outputlocalSWDesisMax &

srun -A rabl -p magic -w node-13 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 1 26 3 &

srun -A rabl -p magic -w node-02 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 1 26 3 > ../logDesisSW/outputrootSWDesisMax &

sleep 300
sleep 60


echo "Min"
srun -A rabl -p magic -w node-14 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 1 27 3 > ../logDesisSW/outputlocalSWDesisMin &

srun -A rabl -p magic -w node-13 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 1 27 3 &

srun -A rabl -p magic -w node-02 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis3.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 1 27 3 > ../logDesisSW/outputrootSWDesisMin &

sleep 300
sleep 60


echo END!!!

