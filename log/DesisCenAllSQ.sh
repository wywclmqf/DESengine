#!/bin/bash

rm -rf ../logDesisCen/output*
echo "Tmbling"
srun -A rabl -p magic -w node-16 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 1 11 3 > ../logDesisCen/outputlocalCenDesisTumbling &

srun -A rabl -p magic -w node-03 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 1 11 3 > ../logDesisCen/outputrootCenDesisTumbling &

sleep 300
sleep 60

echo "Slding"
srun -A rabl -p magic -w node-16 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 1 12 3 > ../logDesisCen/outputlocalCenDesisSliding &

srun -A rabl -p magic -w node-03 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 1 12 3 > ../logDesisCen/outputrootCenDesisSliding &

sleep 300
sleep 60

echo "Punctuation"
srun -A rabl -p magic -w node-16 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 1 13 3 > ../logDesisCen/outputlocalCenDesisPunctuation &


srun -A rabl -p magic -w node-03 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 13 6 3 > ../logDesisCen/outputrootCenDesisPunctuation &

sleep 300
sleep 60

echo "Session"
srun -A rabl -p magic -w node-16 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 1 14 3 > ../logDesisCen/outputlocalCenDesisSession &


srun -A rabl -p magic -w node-03 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 1 14 3 > ../logDesisCen/outputrootCenDesisSession &

sleep 300
sleep 60

echo "Counting"
srun -A rabl -p magic -w node-16 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 1 15 3 > ../logDesisCen/outputlocalCenDesisCounting &

srun -A rabl -p magic -w node-03 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 1 15 3 > ../logDesisCen/outputrootCenDesisCounting &

sleep 300
sleep 60




echo "Median"
srun -A rabl -p magic -w node-16 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 1 21 1 > ../logDesisCen/outputlocalCenDesisMedian &


srun -A rabl -p magic -w node-03 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 1 21 1 > ../logDesisCen/outputrootCenDesisMedian &

sleep 300
sleep 60

echo "Quantile"
srun -A rabl -p magic -w node-16 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 1 22 1 > ../logDesisCen/outputlocalCenDesisQuantile &


srun -A rabl -p magic -w node-03 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 1 22 1 > ../logDesisCen/outputrootCenDesisQuantile &

sleep 300
sleep 60

echo "Average"
srun -A rabl -p magic -w node-16 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 1 23 3 > ../logDesisCen/outputlocalCenDesisAverage &


srun -A rabl -p magic -w node-03 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 1 23 3 > ../logDesisCen/outputrootCenDesisAverage &

sleep 300
sleep 60

echo "Sum"
srun -A rabl -p magic -w node-16 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 1 24 3 > ../logDesisCen/outputlocalCenDesisSum &


srun -A rabl -p magic -w node-03 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 1 24 3 > ../logDesisCen/outputrootCenDesisSum &

sleep 300
sleep 60

echo "Count"
srun -A rabl -p magic -w node-16 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 1 25 3 > ../logDesisCen/outputlocalCenDesisCount &


srun -A rabl -p magic -w node-03 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 1 25 3 > ../logDesisCen/outputrootCenDesisCount &

sleep 300
sleep 60


echo "Max"
srun -A rabl -p magic -w node-16 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 1 26 3 > ../logDesisCen/outputlocalCenDesisMax &


srun -A rabl -p magic -w node-03 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 1 26 3 > ../logDesisCen/outputrootCenDesisMax &

sleep 300
sleep 60


echo "Min"
srun -A rabl -p magic -w node-16 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 1 27 3 > ../logDesisCen/outputlocalCenDesisMin &


srun -A rabl -p magic -w node-03 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program2/Desis5.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 1 27 3 > ../logDesisCen/outputrootCenDesisMin &

sleep 300
sleep 60


echo END!!!

