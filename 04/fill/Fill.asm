// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed.
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
    @5120
    D = A
    @total
    M=D         // set total/32 rows to be colored black
    
    
(LOOP)
    @SCREEN
    D=A
    @addr
    M=D         //set addr to point to screen
    
    @row
    M=0         // set current row to 0
    
    @KBD
    D = M       //load keyboard value and read
    
    @BLACK
    D; JGT
    
    @WHITE
    0; JMP
    
(BLACK)
    @row
    D = M
    @total
    D = M-D
    @LOOP
    D; JEQ          //out of loop if all rows are colored to black
    
    @addr
    A=M
    M = -1          // set current row to black
    
    @row
    M = M+1         //increment row counter
    
    @addr
    M = M+1
    @BLACK
    0; JMP          //go back to the begining of the loop


(WHITE)
    @row
    D = M
    @total
    D = M-D
    @LOOP
    D; JEQ          //out of loop if all rows are colored to white
    
    @addr
    A=M
    M = 0          // set current row to white
    
    @row
    M = M+1         //increment row counter
    
    @addr
    M = M+1
    @WHITE
    0; JMP          //go back to the begining of the loop
