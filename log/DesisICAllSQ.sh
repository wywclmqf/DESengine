#!/bin/bash

rm -rf ../logDesisIC/output*

echo "Tmbling"
srun -A rabl -p magic -w node-12 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 1 11 3 > ../logDesisIC/outputlocalICDesisTumbling &

srun -A rabl -p magic -w node-11 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 1 11 3 &

srun -A rabl -p magic -w node-10 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 1 11 3 > ../logDesisIC/outputrootICDesisTumbling &

sleep 300
sleep 60

echo "Slding"
srun -A rabl -p magic -w node-12 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 1 12 3 > ../logDesisIC/outputlocalICDesisSliding &

srun -A rabl -p magic -w node-11 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 1 12 3 &

srun -A rabl -p magic -w node-10 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 1 12 3 > ../logDesisIC/outputrootICDesisSliding &

sleep 300
sleep 60

echo "Punctuation"
srun -A rabl -p magic -w node-12 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 1 13 3 > ../logDesisIC/outputlocalICDesisPunctuation &

srun -A rabl -p magic -w node-11 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 1 13 3 &

srun -A rabl -p magic -w node-10 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 13 6 3 > ../logDesisIC/outputrootICDesisPunctuation &

sleep 300
sleep 60

echo "Session"
srun -A rabl -p magic -w node-12 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 1 14 3 > ../logDesisIC/outputlocalICDesisSession &

srun -A rabl -p magic -w node-11 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 1 14 3 &

srun -A rabl -p magic -w node-10 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 1 14 3 > ../logDesisIC/outputrootICDesisSession &

sleep 300
sleep 60

echo "Counting"
srun -A rabl -p magic -w node-12 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 1 15 3 > ../logDesisIC/outputlocalICDesisCounting &

srun -A rabl -p magic -w node-11 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 1 15 3 &

srun -A rabl -p magic -w node-10 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 1 15 3 > ../logDesisIC/outputrootICDesisCounting &

sleep 300
sleep 60




echo "Median"
srun -A rabl -p magic -w node-12 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 1 21 1 > ../logDesisIC/outputlocalICDesisMedian &

srun -A rabl -p magic -w node-11 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 1 21 1 &

srun -A rabl -p magic -w node-10 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 1 21 1 > ../logDesisIC/outputrootICDesisMedian &

sleep 300
sleep 60

echo "Quantile"
srun -A rabl -p magic -w node-12 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 1 22 1 > ../logDesisIC/outputlocalICDesisQuantile &

srun -A rabl -p magic -w node-11 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 1 22 1 &

srun -A rabl -p magic -w node-10 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 1 22 1 > ../logDesisIC/outputrootICDesisQuantile &

sleep 300
sleep 60

echo "Average"
srun -A rabl -p magic -w node-12 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 1 23 3 > ../logDesisIC/outputlocalICDesisAverage &

srun -A rabl -p magic -w node-11 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 1 23 3 &

srun -A rabl -p magic -w node-10 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 1 23 3 > ../logDesisIC/outputrootICDesisAverage &

sleep 300
sleep 60

echo "Sum"
srun -A rabl -p magic -w node-12 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 1 24 3 > ../logDesisIC/outputlocalICDesisSum &

srun -A rabl -p magic -w node-11 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 1 24 3 &

srun -A rabl -p magic -w node-10 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 1 24 3 > ../logDesisIC/outputrootICDesisSum &

sleep 300
sleep 60

echo "Count"
srun -A rabl -p magic -w node-12 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 1 25 3 > ../logDesisIC/outputlocalICDesisCount &

srun -A rabl -p magic -w node-11 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 1 25 3 &

srun -A rabl -p magic -w node-10 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 1 25 3 > ../logDesisIC/outputrootICDesisCount &

sleep 300
sleep 60


echo "Max"
srun -A rabl -p magic -w node-12 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 1 26 3 > ../logDesisIC/outputlocalICDesisMax &

srun -A rabl -p magic -w node-11 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 1 26 3 &

srun -A rabl -p magic -w node-10 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 1 26 3 > ../logDesisIC/outputrootICDesisMax &

sleep 300
sleep 60


echo "Min"
srun -A rabl -p magic -w node-12 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 1 27 3 > ../logDesisIC/outputlocalICDesisMin &

srun -A rabl -p magic -w node-11 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 1 27 3 &

srun -A rabl -p magic -w node-10 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis4.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 1 27 3 > ../logDesisIC/outputrootICDesisMin &

sleep 300
sleep 60


echo END!!!

