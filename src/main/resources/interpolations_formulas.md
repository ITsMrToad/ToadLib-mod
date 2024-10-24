## Formulas for interpolations

### Cubiq:

```
   float a = y3 - y2 - y0 + y1;
   float b = y0 - y1 - a;
   float c = y2 - y0;
   return ((a * x + b) * x + c) * x + y1;
```

### Cubic hermite:

```
   float a = -0.5F * d + 1.5F * d1 - 1.5F * d2 + 0.5F * d3;
   float b = d - 2.5F * d1 + 2.0F * d2 - 0.5F * d3;
   float c = -0.5F * d + 0.5F * d2;
   return ((a * x + b) * x + c) * x + d1;
```

### Step:
``` 
  float f = Mth.floor(Math.max(1.0F, ca));
  float f1 = 1.0F;
  if (f <= f1) {
       return a
  } else {
    float f2 = X
    float f3 = Mth.clamp(cb, -f1, f1);
    float f4 = cc;
    if (f3 > 0.0F) {
        f2 = Mth.lerp(f2, EasingDefinitions.EXP.apply(f2, null), f3);
    }

    if (f3 < 0.0F) {
        f2 = Mth.lerp(f2, f1 - EasingDefinitions.EXP.apply(f1 - f2, null), -f3);
    }

    if (f4 > 0.0F) {
        f2 = Mth.ceil(f2 * f) / f;
    } else if (f4 < 0.0) {
        f2 = Math.round(f2 * f) / f;
    } else {
        f2 = Mth.floor(f2 * f) / f;
    }

    return Mth.lerp(ctx.start(), ctx.end(), f2);
```
