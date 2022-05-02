#!/bin/bash

rm -rf ../logDesis/output*
echo "Tmbling"
srun -A rabl -p magic -w node-06 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 1 11 3 > ../logDesis/outputlocalDesisTumbling &

srun -A rabl -p magic -w node-05 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 1 11 3 > ../logDesis/outputinterDesisTumbling &

srun -A rabl -p magic -w node-04 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 1 11 3 > ../logDesis/outputrootDesisTumbling &

sleep 300
sleep 60

echo "Slding"
srun -A rabl -p magic -w node-06 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 1 12 3 > ../logDesis/outputlocalDesisSliding &

srun -A rabl -p magic -w node-05 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 1 12 3 > ../logDesis/outputinterDesisSliding &

srun -A rabl -p magic -w node-04 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 1 12 3 > ../logDesis/outputrootDesisSliding &

sleep 300
sleep 60

echo "Punctuation"
srun -A rabl -p magic -w node-06 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 1 13 3 > ../logDesis/outputlocalDesisPunctuation &

srun -A rabl -p magic -w node-05 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 1 13 3 > ../logDesis/outputinterDesisPunctuation &

srun -A rabl -p magic -w node-04 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 13 6 3 > ../logDesis/outputrootDesisPunctuation &

sleep 300
sleep 60

echo "Session"
srun -A rabl -p magic -w node-06 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 1 14 3 > ../logDesis/outputlocalDesisSession &

srun -A rabl -p magic -w node-05 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 1 14 3 > ../logDesis/outputinterDesisSession &

srun -A rabl -p magic -w node-04 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 1 14 3 > ../logDesis/outputrootDesisSession &

sleep 300
sleep 60

echo "Counting"
srun -A rabl -p magic -w node-06 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 1 15 3 > ../logDesis/outputlocalDesisCounting &

srun -A rabl -p magic -w node-05 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 1 15 3 > ../logDesis/outputinterDesisCounting &

srun -A rabl -p magic -w node-04 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 1 15 3 > ../logDesis/outputrootDesisCounting &

sleep 300
sleep 60




echo "Median"
srun -A rabl -p magic -w node-06 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 1 21 1 > ../logDesis/outputlocalDesisMedian &

srun -A rabl -p magic -w node-05 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 1 21 1 > ../logDesis/outputinterDesisMedian &

srun -A rabl -p magic -w node-04 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 1 21 1 > ../logDesis/outputrootDesisMedian &

sleep 300
sleep 60

echo "Quantile"
srun -A rabl -p magic -w node-06 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 1 22 1 > ../logDesis/outputlocalDesisQuantile &

srun -A rabl -p magic -w node-05 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 1 22 1 > ../logDesis/outputinterDesisQuantile &

srun -A rabl -p magic -w node-04 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 1 22 1 > ../logDesis/outputrootDesisQuantile &

sleep 300
sleep 60

echo "Average"
srun -A rabl -p magic -w node-06 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 1 23 3 > ../logDesis/outputlocalDesisAverage &

srun -A rabl -p magic -w node-05 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 1 23 3 > ../logDesis/outputinterDesisAverage &

srun -A rabl -p magic -w node-04 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 1 23 3 > ../logDesis/outputrootDesisAverage &

sleep 300
sleep 60

echo "Sum"
srun -A rabl -p magic -w node-06 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 1 24 3 > ../logDesis/outputlocalDesisSum &

srun -A rabl -p magic -w node-05 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 1 24 3 > ../logDesis/outputinterDesisSum &

srun -A rabl -p magic -w node-04 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 1 24 3 > ../logDesis/outputrootDesisSum &

sleep 300
sleep 60

echo "Count"
srun -A rabl -p magic -w node-06 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 1 25 3 > ../logDesis/outputlocalDesisCount &

srun -A rabl -p magic -w node-05 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 1 25 3 > ../logDesis/outputinterDesisCount &

srun -A rabl -p magic -w node-04 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 1 25 3 > ../logDesis/outputrootDesisCount &

sleep 300
sleep 60


echo "Max"
srun -A rabl -p magic -w node-06 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 1 26 3 > ../logDesis/outputlocalDesisMax &

srun -A rabl -p magic -w node-05 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 1 26 3 > ../logDesis/outputinterDesisMax &

srun -A rabl -p magic -w node-04 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 1 26 3 > ../logDesis/outputrootDesisMax &

sleep 300
sleep 60


echo "Min"
srun -A rabl -p magic -w node-06 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 1 27 3 > ../logDesis/outputlocalDesisMin &

srun -A rabl -p magic -w node-05 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 1 27 3 > ../logDesis/outputinterDesisMin &

srun -A rabl -p magic -w node-04 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis2.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 1 27 3 > ../logDesis/outputrootDesisMin &

sleep 300
sleep 60


echo END!!!

