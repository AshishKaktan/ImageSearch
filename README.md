**Reverse Image Search** 

This Java Project uses DCT(Discrete Cosine Transform) to reduce the frequencies and Hamming Distance to computer similarity between two Images.

Reference: www.hackerfactor.com

### STEPS  

1. **Reduce Size** : The input image is converted to 32x32 px.
2. **Reduce Color** : The image is reduced to grayscale.
3. **Comupute DTC** : Computer the DTC of 32x32px image.  **DTC.java** available on https://github.com/AshishKaktan/Discrete-Cosine-Transform/
4. **Reduce DTC** : Just Keep the top left 8x8 matrix for further computation, this matrix represent the lowest frequencies.
5. **Compute the average value**: Compute the average value of the Matrix Except the First Value.
6. **Assigning Bits**: Now Depending on whether each of the 64 DCT values is above or below the average set it to 0 or 1.
7. **Construct the hash**: The resultant set of 64 bits is the generated hash for the image.

