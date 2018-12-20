package main

import "fmt"

func main() {
	var a1 uint8 = 1
	var a2 uint16 = 2
	var a3 uint32 = 3
	var a4 uint64 = 4

	var a5 int8 = 5
	var a6 int16 = 6
	var a7 int32 = 7
	var a8 int64 = 8
	fmt.Println("Result:", a1, a2, a3, a4, a5, a6, a7, a8)

	var f1 float32 = 1.0
	var f2 float64 = 3.14
	fmt.Println("Result:", f1, f2)

	var a string = "initial"
	fmt.Println(a)

	var c int = 1997
	fmt.Println(c)

	var d bool = true
	fmt.Println(d)
}
