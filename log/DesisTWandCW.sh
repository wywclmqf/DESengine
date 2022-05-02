#!/bin/bash

rm -rf ../logDesis/output*
rm -rf ../logDesisSW/output*
rm -rf ../logDesisIC/output*
rm -rf ../logDesisCen/output*

for i in 2 10 50 100 500 1000
do

echo $i	
srun -A rabl -p magic -w node-04 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 $i 31 3 > ../logDesis/outputlocalDesisTwCw$i &

srun -A rabl -p magic -w node-03 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 $i 31 3 > ../logDesis/outputinterDesisTwCw$i &

srun -A rabl -p magic -w node-02 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 $i 31 3 > ../logDesis/outputrootDesisTwCw$i &



srun -A rabl -p magic -w node-07 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i 31 3 > ../logDesisSW/outputlocalSWDesisTwCw$i &

srun -A rabl -p magic -w node-06 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i 31 3 &

srun -A rabl -p magic -w node-05 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i 31 3 > ../logDesisSW/outputrootSWDesisTwCw$i &



srun -A rabl -p magic -w node-10 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 31 3 > ../logDesisIC/outputlocalICDesisTwCw$i &

srun -A rabl -p magic -w node-09 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 31 3 &

srun -A rabl -p magic -w node-08 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 31 3 > ../logDesisIC/outputrootICDesisTwCw$i &


srun -A rabl -p magic -w node-12 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i 31 3 > ../logDesisCen/outputlocalCenDesisTwCw$i &

srun -A rabl -p magic -w node-11 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i 31 3 > ../logDesisCen/outputrootCenDesisTwCw$i &

sleep 300

sleep 60
done


for i in 2 10 50 100 500 1000
do

echo $i	
srun -A rabl -p magic -w node-04 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EntryPointForLocalNode 4 $i 32 3 > ../logDesis/outputlocalDesisCwCw$i &

srun -A rabl -p magic -w node-03 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForIntermediaNode 2 $i 32 3 > ../logDesis/outputinterDesisCwCw$i &

srun -A rabl -p magic -w node-02 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.Desis.EntryPoint.EnteryPointForRootnode 1 $i 32 3 > ../logDesis/outputrootDesisCwCw$i &



srun -A rabl -p magic -w node-07 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EntryPointForLocalNode 4 $i 32 3 > ../logDesisSW/outputlocalSWDesisCwCw$i &

srun -A rabl -p magic -w node-06 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForIntermediaNode 2 $i 32 3 &

srun -A rabl -p magic -w node-05 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisSW.EntryPoint.EnteryPointForRootnode 1 $i 32 3 > ../logDesisSW/outputrootSWDesisCwCw$i &



srun -A rabl -p magic -w node-10 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EntryPointForLocalNode 4 $i 32 3 > ../logDesisIC/outputlocalICDesisCwCw$i &

srun -A rabl -p magic -w node-09 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForIntermediaNode 2 $i 32 3 &

srun -A rabl -p magic -w node-08 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisIC.EntryPoint.EnteryPointForRootnode 1 $i 32 3 > ../logDesisIC/outputrootICDesisCwCw$i &


srun -A rabl -p magic -w node-12 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i 32 3 > ../logDesisCen/outputlocalCenDesisCwCw$i &

srun -A rabl -p magic -w node-11 -t 5 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../program/Desis.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i 32 3 > ../logDesisCen/outputrootCenDesisCwCw$i &

sleep 300

sleep 60
done


echo END!!!

