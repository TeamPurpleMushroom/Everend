// a permutation table of the numbers 0-255 in a random order.
const int[] PERMS = int[](128, 111, 168, 210, 233, 58, 199, 93, 160, 17, 6, 60, 36, 135, 100, 185, 42, 211, 134, 222, 8, 53, 4, 131, 182, 89, 241, 90, 252, 86, 205, 200, 59, 238, 145, 188, 196, 183, 189, 242, 226, 76, 190, 12, 208, 251, 149, 79, 108, 177, 44, 218, 159, 29, 126, 98, 151, 101, 27, 99, 178, 154, 221, 232, 231, 245, 150, 176, 41, 95, 64, 206, 153, 120, 34, 181, 198, 5, 47, 192, 35, 3, 43, 167, 216, 229, 227, 119, 180, 236, 139, 117, 28, 164, 237, 96, 255, 31, 94, 74, 9, 249, 143, 38, 40, 201, 103, 124, 138, 118, 105, 57, 67, 207, 72, 88, 1, 110, 16, 52, 51, 56, 220, 224, 114, 215, 0, 37, 92, 162, 169, 141, 61, 125, 225, 113, 91, 102, 123, 202, 13, 244, 121, 104, 136, 45, 10, 116, 223, 246, 137, 7, 213, 50, 115, 165, 142, 187, 81, 83, 70, 230, 78, 77, 75, 132, 66, 194, 54, 19, 20, 109, 155, 146, 39, 186, 144, 234, 22, 62, 235, 30, 147, 18, 21, 122, 195, 148, 33, 2, 82, 85, 152, 203, 174, 212, 97, 73, 172, 170, 69, 87, 219, 250, 254, 204, 55, 48, 107, 112, 209, 239, 140, 166, 175, 49, 243, 161, 157, 65, 247, 127, 129, 191, 156, 173, 14, 23, 106, 197, 84, 15, 133, 228, 179, 63, 253, 163, 26, 193, 11, 240, 130, 80, 25, 32, 214, 71, 248, 46, 217, 68, 184, 171, 24, 158, 128, 111, 168, 210, 233, 58, 199, 93, 160, 17, 6, 60, 36, 135, 100, 185, 42, 211, 134, 222, 8, 53, 4, 131, 182, 89, 241, 90, 252, 86, 205, 200, 59, 238, 145, 188, 196, 183, 189, 242, 226, 76, 190, 12, 208, 251, 149, 79, 108, 177, 44, 218, 159, 29, 126, 98, 151, 101, 27, 99, 178, 154, 221, 232, 231, 245, 150, 176, 41, 95, 64, 206, 153, 120, 34, 181, 198, 5, 47, 192, 35, 3, 43, 167, 216, 229, 227, 119, 180, 236, 139, 117, 28, 164, 237, 96, 255, 31, 94, 74, 9, 249, 143, 38, 40, 201, 103, 124, 138, 118, 105, 57, 67, 207, 72, 88, 1, 110, 16, 52, 51, 56, 220, 224, 114, 215, 0, 37, 92, 162, 169, 141, 61, 125, 225, 113, 91, 102, 123, 202, 13, 244, 121, 104, 136, 45, 10, 116, 223, 246, 137, 7, 213, 50, 115, 165, 142, 187, 81, 83, 70, 230, 78, 77, 75, 132, 66, 194, 54, 19, 20, 109, 155, 146, 39, 186, 144, 234, 22, 62, 235, 30, 147, 18, 21, 122, 195, 148, 33, 2, 82, 85, 152, 203, 174, 212, 97, 73, 172, 170, 69, 87, 219, 250, 254, 204, 55, 48, 107, 112, 209, 239, 140, 166, 175, 49, 243, 161, 157, 65, 247, 127, 129, 191, 156, 173, 14, 23, 106, 197, 84, 15, 133, 228, 179, 63, 253, 163, 26, 193, 11, 240, 130, 80, 25, 32, 214, 71, 248, 46, 217, 68, 184, 171, 24, 158, 128, 111, 168, 210, 233, 58, 199, 93, 160, 17, 6, 60, 36, 135, 100, 185, 42, 211, 134, 222, 8, 53, 4, 131, 182, 89, 241, 90, 252, 86, 205, 200, 59, 238, 145, 188, 196, 183, 189, 242, 226, 76, 190, 12, 208, 251, 149, 79, 108, 177, 44, 218, 159, 29, 126, 98, 151, 101, 27, 99, 178, 154, 221, 232, 231, 245, 150, 176, 41, 95, 64, 206, 153, 120, 34, 181, 198, 5, 47, 192, 35, 3, 43, 167, 216, 229, 227, 119, 180, 236, 139, 117, 28, 164, 237, 96, 255, 31, 94, 74, 9, 249, 143, 38, 40, 201, 103, 124, 138, 118, 105, 57, 67, 207, 72, 88, 1, 110, 16, 52, 51, 56, 220, 224, 114, 215, 0, 37, 92, 162, 169, 141, 61, 125, 225, 113, 91, 102, 123, 202, 13, 244, 121, 104, 136, 45, 10, 116, 223, 246, 137, 7, 213, 50, 115, 165, 142, 187, 81, 83, 70, 230, 78, 77, 75, 132, 66, 194, 54, 19, 20, 109, 155, 146, 39, 186, 144, 234, 22, 62, 235, 30, 147, 18, 21, 122, 195, 148, 33, 2, 82, 85, 152, 203, 174, 212, 97, 73, 172, 170, 69, 87, 219, 250, 254, 204, 55, 48, 107, 112, 209, 239, 140, 166, 175, 49, 243, 161, 157, 65, 247, 127, 129, 191, 156, 173, 14, 23, 106, 197, 84, 15, 133, 228, 179, 63, 253, 163, 26, 193, 11, 240, 130, 80, 25, 32, 214, 71, 248, 46, 217, 68, 184, 171, 24, 158);

float ease(float num) {
    return (((6.0 * num) - 15.0) * num + 10.0) * num * num * num;
}

vec2 getVector2D(int i) {
    i = i & 3;
    if (i == 0) return vec2(1.0, 1.0);
    if (i == 1) return vec2(-1.0, 1.0);
    if (i == 2) return vec2(1.0, -1.0);
    return vec2(-1.0, -1.0);
}

vec3 getVector3D(int i) {
    //float component = 0.5773502691896258;
    float component = 1.0;

    i = i & 7;
    if (i == 0) return vec3(component, component, component);
    if (i == 1) return vec3(-component, component, component);
    if (i == 2) return vec3(component, -component, component);
    if (i == 3) return vec3(-component, -component, component);
    if (i == 4) return vec3(component, component, -component);
    if (i == 5) return vec3(-component, component, -component);
    if (i == 6) return vec3(component, -component, -component);
    return vec3(-component, -component, -component);
}

float lerp(float num1, float num2, float lerpAmount) {
    return num1 + (num2 - num1) * lerpAmount;
}

/*
    uses 2D perlin noise to generate values in the range (-amplitude, amplitude)
    higher frequency means the image is "compressed"
*/
float perlin2D(vec2 coords, float frequency, float amplitude) {
    coords = coords * frequency;
    vec2 coordsf = vec2(coords.x - floor(coords.x), coords.y - floor(coords.y));

    /*
        Input values are said to be on an integer grid. Decimal values lie inside a square in that grid.
        For each of the corners where the input lies, a value is generated.
        This value is the dot product of 2 vectors.
        The first vector comes from a grid point to the input value.
     */
    vec2 topRight = vec2(coordsf.x - 1.0, coordsf.y - 1.0);
    vec2 topLeft = vec2(coordsf.x, coordsf.y - 1.0);
    vec2 bottomRight = vec2(coordsf.x - 1.0, coordsf.y);
    vec2 bottomLeft = vec2(coordsf.x, coordsf.y);

    /*
        The second vector should be "random", but consistent for each grid point.
        We use the permutation table to obtain it (RNG could be used, but is more expensive).

        First we use the bitwise & operator (in this case works like % 256) to obtain indexes for the permutation table.
        Keep in mind we can also access permX + 1 and permY + 1 due to the fact that we duplicated the table.
     */
    int permX = (int(floor(coords.x))) % PERMS.length();
    int permX2 = (permX + 1) % PERMS.length();
    int permY = (int(floor(coords.y))) % PERMS.length();
    int permY2 = (permY + 1) % PERMS.length();

    int valueTopRight = PERMS[PERMS[permX + 1] + permY + 1];
    int valueTopLeft = PERMS[PERMS[permX] + permY + 1];
    int valueBottomRight = PERMS[PERMS[permX + 1] + permY];
    int valueBottomLeft = PERMS[PERMS[permX] + permY];

    /*
        Calculate the dot products. We finally have the special values for each grid corner.
     */
    float dotTopRight = dot(topRight, getVector2D(valueTopRight));
    float dotTopLeft = dot(topLeft, getVector2D(valueTopLeft));
    float dotBottomRight = dot(bottomRight, getVector2D(valueBottomRight));
    float dotBottomLeft = dot(bottomLeft, getVector2D(valueBottomLeft));

    /*
        Finally, we begin interpolating these values.
        Since we can only interpolate two numbers at a time, we interpolate 2 pairs and then interpolate their results.
        Also, using linear interpolation will produce sharp edges.
        We use the ease function to improve our inputs to the interpolation function.
     */
    float u = ease(coordsf.x);
    float v = ease(coordsf.y);
    return lerp(lerp(dotBottomLeft, dotTopLeft, v), lerp(dotBottomRight, dotTopRight, v), u) * amplitude;
}

float perlin2DLoop(vec2 coords, float frequency, float amplitude, float limit) {
    int loopPoint = min(int(floor(limit * frequency)), PERMS.length());

    coords = coords * frequency;
    vec2 coordsf = vec2(coords.x - floor(coords.x), coords.y - floor(coords.y));

    /*
        Input values are said to be on an integer grid. Decimal values lie inside a square in that grid.
        For each of the corners where the input lies, a value is generated.
        This value is the dot product of 2 vectors.
        The first vector comes from a grid point to the input value.
     */
    vec2 topRight = vec2(coordsf.x - 1.0, coordsf.y - 1.0);
    vec2 topLeft = vec2(coordsf.x, coordsf.y - 1.0);
    vec2 bottomRight = vec2(coordsf.x - 1.0, coordsf.y);
    vec2 bottomLeft = vec2(coordsf.x, coordsf.y);

    /*
        The second vector should be "random", but consistent for each grid point.
        We use the permutation table to obtain it (RNG could be used, but is more expensive).

        First we use the bitwise & operator (in this case works like % 256) to obtain indexes for the permutation table.
        Keep in mind we can also access permX + 1 and permY + 1 due to the fact that we duplicated the table.
     */
    int permX = (int(floor(coords.x))) % loopPoint;
    int permX2 = (permX + 1) % loopPoint;
    int permY = (int(floor(coords.y))) % loopPoint;
    int permY2 = (permY + 1) % loopPoint;

    int valueTopRight = PERMS[PERMS[permX2] + permY2];
    int valueTopLeft = PERMS[PERMS[permX] + permY2];
    int valueBottomRight = PERMS[PERMS[permX2] + permY];
    int valueBottomLeft = PERMS[PERMS[permX] + permY];

    /*
        Calculate the dot products. We finally have the special values for each grid corner.
     */
    float dotTopRight = dot(topRight, getVector2D(valueTopRight));
    float dotTopLeft = dot(topLeft, getVector2D(valueTopLeft));
    float dotBottomRight = dot(bottomRight, getVector2D(valueBottomRight));
    float dotBottomLeft = dot(bottomLeft, getVector2D(valueBottomLeft));

    /*
        Finally, we begin interpolating these values.
        Since we can only interpolate two numbers at a time, we interpolate 2 pairs and then interpolate their results.
        Also, using linear interpolation will produce sharp edges.
        We use the ease function to improve our inputs to the interpolation function.
     */
    float u = ease(coordsf.x);
    float v = ease(coordsf.y);
    return lerp(lerp(dotBottomLeft, dotTopLeft, v), lerp(dotBottomRight, dotTopRight, v), u) * amplitude;
}

/*
    uses 2D perlin noise to generate values in the range (-amplitude, amplitude)
    higher frequency means the image is "compressed"
*/
float perlin3D(vec3 coords, float frequency, float amplitude) {
    coords = coords * frequency;
    vec3 coordsf = vec3(coords.x - floor(coords.x), coords.y - floor(coords.y), coords.z - floor(coords.z));

    /*
        Input values are said to be on an integer grid. Decimal values lie inside a square in that grid.
        For each of the corners where the input lies, a value is generated.
        This value is the dot product of 2 vectors.
        The first vector comes from a grid point to the input value.
     */
    vec3 yPxPzP = vec3(coordsf.x - 1.0, coordsf.y - 1.0, coordsf.z - 1.0);
    vec3 yPxNzP = vec3(coordsf.x, coordsf.y - 1.0, coordsf.z - 1.0);
    vec3 yNxPzP = vec3(coordsf.x - 1.0, coordsf.y, coordsf.z - 1.0);
    vec3 yNxNzP = vec3(coordsf.x, coordsf.y, coordsf.z - 1.0);
    vec3 yPxPzN = vec3(coordsf.x - 1.0, coordsf.y - 1.0, coordsf.z);
    vec3 yPxNzN = vec3(coordsf.x, coordsf.y - 1.0, coordsf.z);
    vec3 yNxPzN = vec3(coordsf.x - 1.0, coordsf.y, coordsf.z);
    vec3 yNxNzN = vec3(coordsf.x, coordsf.y, coordsf.z);

    /*
        The second vector should be "random", but consistent for each grid point.
        We use the permutation table to obtain it (RNG could be used, but is more expensive).

        First we use the bitwise & operator (in this case works like % 256) to obtain indexes for the permutation table.
        Keep in mind we can also access permX + 1 and permY + 1 due to the fact that we duplicated the table.
     */
    int permX = (int(floor(coords.x))) % PERMS.length();
    int permX2 = (permX + 1) % PERMS.length();
    int permY = (int(floor(coords.y))) % PERMS.length();
    int permY2 = (permY + 1) % PERMS.length();
    int permZ = (int(floor(coords.z))) % PERMS.length();
    int permZ2 = (permZ + 1) % PERMS.length();

    int valueyPxPzP = PERMS[PERMS[PERMS[permX + 1] + permY + 1] + 1];
    int valueyPxNzP = PERMS[PERMS[PERMS[permX] + permY + 1] + 1];
    int valueyNxPzP = PERMS[PERMS[PERMS[permX + 1] + permY] + 1];
    int valueyNxNzP = PERMS[PERMS[PERMS[permX] + permY] + 1];
    int valueyPxPzN = PERMS[PERMS[PERMS[permX + 1] + permY + 1]];
    int valueyPxNzN = PERMS[PERMS[PERMS[permX] + permY + 1]];
    int valueyNxPzN = PERMS[PERMS[PERMS[permX + 1] + permY]];
    int valueyNxNzN = PERMS[PERMS[PERMS[permX] + permY]];

    /*
        Calculate the dot products. We finally have the special values for each grid corner.
     */
    float dotyPxPzP = dot(yPxPzP, getVector3D(valueyPxPzP));
    float dotyPxNzP = dot(yPxNzP, getVector3D(valueyPxNzP));
    float dotyNxPzP = dot(yNxPzP, getVector3D(valueyNxPzP));
    float dotyNxNzP = dot(yNxNzP, getVector3D(valueyNxNzP));
    float dotyPxPzN = dot(yPxPzN, getVector3D(valueyPxPzN));
    float dotyPxNzN = dot(yPxNzN, getVector3D(valueyPxNzN));
    float dotyNxPzN = dot(yNxPzN, getVector3D(valueyNxPzN));
    float dotyNxNzN = dot(yNxNzN, getVector3D(valueyNxNzN));

    /*
        Finally, we begin interpolating these values.
        Since we can only interpolate two numbers at a time, we interpolate 2 pairs and then interpolate their results.
        Also, using linear interpolation will produce sharp edges.
        We use the ease function to improve our inputs to the interpolation function.
     */
    float u = ease(coordsf.x);
    float v = ease(coordsf.y);
    float w = ease(coordsf.z);

    float zP = lerp(lerp(dotyNxNzP, dotyPxNzP, v), lerp(dotyNxPzP, dotyPxPzP, v), u);
    float zN = lerp(lerp(dotyNxNzN, dotyPxNzN, v), lerp(dotyNxPzN, dotyPxPzN, v), u);

    return lerp(zN, zP, w) * amplitude;
}